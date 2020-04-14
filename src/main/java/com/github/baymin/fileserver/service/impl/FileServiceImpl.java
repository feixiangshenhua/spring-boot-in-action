package com.github.baymin.fileserver.service.impl;

import com.github.baymin.fileserver.entity.FileInfo;
import com.github.baymin.fileserver.repository.FileInfoRepository;
import com.github.baymin.fileserver.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * 文件业务操作接口实现类
 *
 * @author Zongwei
 * @date 2020/4/13 23:18
 */
@Service
public class FileServiceImpl implements FileService {

    private static final String FILE_PATH = "/usr/local/modules/File-Server/data/%s/";

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Override
    public Mono<FileInfo> uploadFile(FilePart filePart) {
//        Path tempFile = Files.createTempFile("D://", filePart.filename());
//        // NOTE 方法一
//        AsynchronousFileChannel channel = AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
//        DataBufferUtils.write(filePart.content(), channel, 0)
//                .doOnComplete(() -> {
//                    log.info("上传文件成功");
//                })
//                .subscribe();
        // NOTE 方法二
        String filePath = String.format(FILE_PATH, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String suffix = filePart.filename().substring(filePart.filename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;

        filePart.transferTo(new File(filePath + fileName));
        FileInfo fileInfo = new FileInfo();
        fileInfo.setOriginFileName(filePart.filename());
        fileInfo.setFileName(fileName);
        fileInfo.setAbsolutePath(filePath);
        fileInfo.setCreatedAt(new Date());
        return fileInfoRepository.insert(fileInfo);
    }

    @Override
    public Mono<FileInfo> getFileById(String fileId) {
        return fileInfoRepository.findById(fileId);
//        return Mono.empty();
//        return Mono.create(fileInfoMonoSink -> {
//            FileInfo fileInfo = new FileInfo();
//            fileInfo.setAbsolutePath("C:\\Users\\zongwei\\workspace\\zongwei\\springboot-file-server\\src\\main\\resources\\myimage.png");
//            fileInfo.setFileName("myimage.png");
//            fileInfoMonoSink.success(fileInfo);
//        });
    }

    @Override
    public Mono<Void> deleteById(String fileId) {
        Mono<FileInfo> fileInfoMono = this.getFileById(fileId);
        Mono<FileInfo> fallback = Mono.error(new FileNotFoundException("No file was found with fileId: " + fileId));
        return fileInfoMono
                .switchIfEmpty(fallback)
                .flatMap(fileInfo -> {
                    File file = new File(fileInfo.getAbsolutePath() + fileInfo.getFileName());
                    file.delete();
                    return fileInfoRepository.deleteById(fileId);
                }).then();
    }
}
