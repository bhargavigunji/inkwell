package com.inkwell.service;

import com.inkwell.entity.Tag;
import com.inkwell.repo.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    TagRepo tagRepo;

    @Override
    public Tag createTag(Tag tag) {
        return tagRepo.save(tag);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepo.findAll();
    }

    @Override
    public Tag getTagById(Long id) {
        return tagRepo.findById(id).orElseThrow(()-> new RuntimeException("Tag not found"));
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag existingTag = tagRepo.findById(id).orElseThrow(()-> new RuntimeException("Tag not found"));

        existingTag.setName(tag.getName());
        return tagRepo.save(existingTag);
    }

    @Override
    public void deleteTag(Long id) {

        Tag existingTag = tagRepo.findById(id).orElseThrow(()-> new RuntimeException("Tag not found"));

        tagRepo.delete(existingTag);

    }
}
