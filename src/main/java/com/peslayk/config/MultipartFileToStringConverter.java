package com.peslayk.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

@ConfigurationPropertiesBinding
public class MultipartFileToStringConverter implements Converter<MultipartFile, String> {

    @Override
    public String convert(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename();
    }
}
