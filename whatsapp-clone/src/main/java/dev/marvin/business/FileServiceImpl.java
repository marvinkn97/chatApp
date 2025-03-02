package dev.marvin.business;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String saveFile(MultipartFile file, String senderId) {
        return "";
    }
}
