package com.spyke.gcpbigquery.service;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.Field;
import com.google.cloud.bigquery.FormatOptions;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.JobStatistics;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.StandardSQLTypeName;
import com.google.cloud.bigquery.TableId;
import com.google.cloud.bigquery.WriteChannelConfiguration;
import com.spyke.gcpbigquery.domain.entities.Device;
import com.spyke.gcpbigquery.domain.repositories.DeviceRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class DataExtractorService {
    private BigQuery bigQuery;
    private DeviceRepository deviceRepository;

    @Value("${spring.cloud.gcp.bigquery.dataset-name}")
    private final String bigQueryDataSetName;
    @Value("${spring.cloud.gcp.bigquery.dataset-location}")
    private final String bigQueryDataSetLocation;

    private final Logger log = LoggerFactory.getLogger(DataExtractorService.class);

    public void uploadDevicesToBigQuery(
            final String bigQueryTable, final boolean overwrite, final List<Device> deviceList
    ) {
        log.info(
                "Upload devices to BigQuery table [{}/{}]",
                bigQueryDataSetName,
                bigQueryTable
        );

        try {
            final var bigQueryTableId = TableId.of(bigQueryDataSetName, bigQueryTable);

            final var bigQueryTableFields = new ArrayList<Field>();
            bigQueryTableFields.add(Field.of("id", StandardSQLTypeName.valueOf("NUMERIC")));
            bigQueryTableFields.add(Field.of("name", StandardSQLTypeName.valueOf("STRING")));

            final var writeChannelConfiguration =
                    WriteChannelConfiguration
                            .newBuilder(bigQueryTableId)
                            .setFormatOptions(FormatOptions.csv())
                            .setCreateDisposition(JobInfo.CreateDisposition.CREATE_IF_NEEDED)
                            .setWriteDisposition(overwrite ? JobInfo.WriteDisposition.WRITE_TRUNCATE
                                                           : JobInfo.WriteDisposition.WRITE_APPEND)
                            .setSchema(Schema.of(bigQueryTableFields))
                            .build();

            // The location must be specified; other fields can be auto-detected.
            final var jobId = JobId.newBuilder().setLocation(bigQueryDataSetLocation).build();
            final var writer = bigQuery.writer(jobId, writeChannelConfiguration);

            final var csvFile = Files.createTempFile("extract_data", ".csv").toFile();
            // Write data to writer
            final var out = new FileWriter(csvFile);
            final var printer = new CSVPrinter(out, CSVFormat.DEFAULT);

            for (final Device device : deviceList) {
                final var values = new ArrayList<>();
                values.add(device.getId());
                values.add(device.getName());
                printer.printRecord(values);
            }
            printer.close(true);

            // Get load job
            final var job = writer.getJob();
            final var finishedJob = job.waitFor();
            final var stats = (JobStatistics.LoadStatistics) finishedJob.getStatistics();
            log.info(
                    "Wrote {} records to BigQuery table [{}/{}]",
                    stats.getOutputRows(),
                    bigQueryDataSetName,
                    bigQueryTable
            );
        } catch (IOException | InterruptedException | BigQueryException ex) {
            log.error("Error occured during writing of file to BigQuery", ex);
        }
    }

    public void getAndUploadDevicesToBigQuery() {
        final var devices = deviceRepository.findAll();
        this.uploadDevicesToBigQuery("devices", false, devices);
    }
}
