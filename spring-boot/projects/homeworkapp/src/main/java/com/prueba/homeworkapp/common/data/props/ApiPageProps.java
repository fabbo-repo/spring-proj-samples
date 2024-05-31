package com.prueba.homeworkapp.common.data.props;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ApiPageProps {
    private int pageNum;

    private int pageSize;
}
