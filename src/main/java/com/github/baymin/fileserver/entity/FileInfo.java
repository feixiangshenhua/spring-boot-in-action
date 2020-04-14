package com.github.baymin.fileserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * 文件信息
 *
 * @author Zongwei
 * @date 2020/4/13 23:20
 */
@Data
@Document(collection = "file_info")
public class FileInfo {

    @Id
    private String id;

    @Field(name = "origin_file_name")
    private String originFileName;

    @Field(name = "file_name")
    private String fileName;

    @JsonIgnore
    @Field(name = "absolute_path")
    private String absolutePath;

    @Field(name = "created_at")
    private Date createdAt;

    @JsonIgnore
    @Field(name = "md5")
    private String md5;

}
