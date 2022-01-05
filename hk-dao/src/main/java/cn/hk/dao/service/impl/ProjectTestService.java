package cn.hk.dao.service.impl;

import cn.hk.dao.mapper.ProjectTestMapper;
import cn.hk.dao.service.IProjectTestService;
import cn.hk.model.po.ProjectTest;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProjectTestService extends ServiceImpl<ProjectTestMapper, ProjectTest> implements IProjectTestService {
}
