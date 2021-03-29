package com.yz.blog.service;

import com.yz.blog.Bean.Type;

import com.yz.blog.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class TypeServiceImpl implements TypeService {
    @Resource
    private com.yz.blog.dao.typeRepositoryDao typeRepositoryDao;

    @Transactional
    @Override
    public int saveType(Type type) {
        return typeRepositoryDao.saveType(type);

    }

    @Transactional
    @Override
    public Type getType(Long id) {

        return typeRepositoryDao.getType(id);

    }

    @Transactional
    @Override
    public List<Type> getAllType() {
        return typeRepositoryDao.getAllType();
    }

    @Transactional
    @Override
    public int updateType(Type type) {
        return typeRepositoryDao.updateType(type);

    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepositoryDao.deleteType(id);

    }


    @Override
    public Type getTypeByName(String name) {
        return typeRepositoryDao.getTypeByName(name);
    }

    // 指定数量去查询type
    public List<Type> getSizeTypes(Long size){
        return typeRepositoryDao.getSizeTypes(size);
    }


}
