package org.perscholas.cmsapp.dao;

import org.perscholas.cmsapp.models.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public interface ImageInfoRepoI extends JpaRepository<ImageInfo,Integer> {
    ImageInfo findByName(String name);
}
