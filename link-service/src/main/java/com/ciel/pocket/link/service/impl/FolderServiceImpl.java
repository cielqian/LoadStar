package com.ciel.pocket.link.service.impl;

import com.ciel.pocket.infrastructure.exceptions.ObjectNotExistingException;
import com.ciel.pocket.link.domain.Folder;
import com.ciel.pocket.link.dto.output.FolderTreeOutput;
import com.ciel.pocket.link.repository.FolderRepository;
import com.ciel.pocket.link.service.FolderService;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/22 13:28
 */
@Service
public class FolderServiceImpl implements FolderService {
    @Autowired
    FolderRepository folderRepository;

    @Override
    public Long create(Folder folder) {
        if (folder.getParentId() == 0 || folder.getParentId() == null){
            folder.setDeep(0);
        }
        else{
            Optional<Folder> parent = folderRepository.findById(folder.getParentId());
            if (!parent.isPresent())
                throw new ObjectNotExistingException("父级文件夹不存在");
            folder.setDeep(parent.get().getDeep() + 1);
        }

        folderRepository.save(folder);
        return folder.getId();
    }

    @Override
    public List<FolderTreeOutput> queryFolderTree(Long userId) {
        List<FolderTreeOutput> folderTreeOutputs = new ArrayList<>();

        List<Folder> folders = folderRepository.queryAllByUserIdAndIsDeleteEquals(userId, false);
        if (folders != null && folders.size() > 0) {
            List<Folder> roots = folders.stream().filter(x -> x.getDeep() == 0).collect(Collectors.toList());
            List<Long> parentIds = new ArrayList<>();
            roots.forEach(x -> {
                FolderTreeOutput element = new FolderTreeOutput();
                element.setId(x.getId());
                element.setName(x.getName());
                element.setSystem(x.isSystem());
                folderTreeOutputs.add(element);
                parentIds.add(x.getId());
                c(folders, element);
            });
        }
        return folderTreeOutputs;
    }

    private void c(List<Folder> all, FolderTreeOutput root){
        List<Folder> elements = all.stream().filter(y -> y.getParentId().equals(root.getId())).collect(Collectors.toList());
        elements.forEach(z -> {
            FolderTreeOutput element = new FolderTreeOutput();
            element.setId(z.getId());
            element.setName(z.getName());
            element.setSystem(z.isSystem());
            root.getChilds().add(element);
            c(all, element);
        });
    }
}
