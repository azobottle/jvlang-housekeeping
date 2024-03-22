package com.jvlang.housekeeping.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DateConverter implements Converter<Date> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Date convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (cellData.getType().equals(CellDataTypeEnum.NUMBER)) {
            LocalDate localDate = LocalDate.of(1900, 1, 1);
            //excel 有些奇怪的bug, 导致日期数差2
            localDate = localDate.plusDays(cellData.getNumberValue().longValue() - 2);
            return Date.valueOf(localDate);
        } else if (cellData.getType().equals(CellDataTypeEnum.STRING)) {
            return Date.valueOf(LocalDate.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        } else {
            return null;
        }
    }

    @Override
    public WriteCellData<?> convertToExcelData(Date value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(value.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    }
}

