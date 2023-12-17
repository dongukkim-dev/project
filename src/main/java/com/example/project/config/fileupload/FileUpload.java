package com.example.project.config.fileupload;

import com.example.project.dto.item.AddItemRequest;
import com.example.project.dto.item.UpdateItemRequest;
import com.example.project.dto.review.AddReviewRequest;
import com.example.project.dto.store.AddStoreRequest;
import com.example.project.dto.store.UpdateStoreRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class FileUpload {

    public boolean uploadReviewImg(AddReviewRequest request) {

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String uploadFolder= Paths.get("C:", "delivery", "upload").toString();
        String imageUploadFolder = Paths.get("reviewImg", today).toString();
        String uploadPath = Paths.get(uploadFolder, imageUploadFolder).toString();

        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        UUID uuid = UUID.randomUUID();
        String reviewImgName = uuid+"_" + request.getFile().getOriginalFilename();


        try {
            File target = new File(uploadPath, reviewImgName);
            request.getFile().transferTo(target);

        } catch (Exception e) {
            return false;
        }


        request.setPicture("upload\\" + imageUploadFolder + "\\" + reviewImgName);


        return true;
    }

    public boolean uploadItemImg(AddItemRequest request, MultipartFile file) {

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String uploadFolder= Paths.get("C:", "delivery", "upload").toString();
        String imageUploadFolder = Paths.get("itemImg", today).toString();
        String uploadPath = Paths.get(uploadFolder, imageUploadFolder).toString();

        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        UUID uuid = UUID.randomUUID();
        String itemImgName = uuid+"_" + file.getOriginalFilename();


        try {
            File target = new File(uploadPath, itemImgName);
            file.transferTo(target);

        } catch (Exception e) {
            return false;
        }


        request.setPicture("upload\\" + imageUploadFolder + "\\" + itemImgName);


        return true;
    }

    public boolean uploadItemImg(UpdateItemRequest request, MultipartFile file) {

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String uploadFolder= Paths.get("C:", "delivery", "upload").toString();
        String imageUploadFolder = Paths.get("itemImg", today).toString();
        String uploadPath = Paths.get(uploadFolder, imageUploadFolder).toString();

        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        UUID uuid = UUID.randomUUID();
        String itemImgName = uuid+"_" + file.getOriginalFilename();


        try {
            File target = new File(uploadPath, itemImgName);
            file.transferTo(target);

        } catch (Exception e) {
            return false;
        }


        request.setPicture("upload\\" + imageUploadFolder + "\\" + itemImgName);


        return true;
    }

    public boolean uploadStoreImg(AddStoreRequest request, MultipartFile file) {

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String uploadFolder= Paths.get("C:", "delivery", "upload").toString();
        String imageUploadFolder = Paths.get("storeImg", today).toString();
        String uploadPath = Paths.get(uploadFolder, imageUploadFolder).toString();

        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        UUID uuid = UUID.randomUUID();
        String storeImgName = uuid+"_" + file.getOriginalFilename();


        try {
            File target = new File(uploadPath, storeImgName);
            file.transferTo(target);

        } catch (Exception e) {
            return false;
        }


        request.setPicture("upload\\" + imageUploadFolder + "\\" + storeImgName);


        return true;
    }

    public boolean uploadStoreImg(UpdateStoreRequest request, MultipartFile file) {

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String uploadFolder= Paths.get("C:", "delivery", "upload").toString();
        String imageUploadFolder = Paths.get("storeImg", today).toString();
        String uploadPath = Paths.get(uploadFolder, imageUploadFolder).toString();

        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        UUID uuid = UUID.randomUUID();
        String storeImgName = uuid+"_" + file.getOriginalFilename();


        try {
            File target = new File(uploadPath, storeImgName);
            file.transferTo(target);

        } catch (Exception e) {
            return false;
        }


        request.setPicture("upload\\" + imageUploadFolder + "\\" + storeImgName);


        return true;
    }
}
