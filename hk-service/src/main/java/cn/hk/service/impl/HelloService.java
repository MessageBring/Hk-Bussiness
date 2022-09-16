package cn.hk.service.impl;

import cn.hk.common.BusinessException;
import cn.hk.common.enums.RespEnums;
import cn.hk.dao.service.IProjectTestService;
import cn.hk.model.dto.ProjectTestDTO;
import cn.hk.model.po.ProjectTest;
import cn.hk.model.vo.ProjectTestVo;
import cn.hk.service.IHelloService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HelloService implements IHelloService {

    private final IProjectTestService projectTestService;

    @Override
    public List<ProjectTestVo> queryAllProjectTest() {
        List<ProjectTest> projectTests = projectTestService.list();
        List<ProjectTestVo> projectTestVos = null;
        if (!CollectionUtils.isEmpty(projectTests)){
            projectTestVos = new ArrayList<>();
            for (ProjectTest test:projectTests){
                ProjectTestVo projectTestVo = new ProjectTestVo();
                projectTestVo.setTestDesc(test.getTestDesc());
                projectTestVos.add(projectTestVo);
            }
        }
        return projectTestVos;
    }

    @Override
    public void addOneProjectTestRecord(ProjectTestDTO projectTestDTO) {
        if (projectTestDTO==null){
            throw new IllegalArgumentException("projectTestDTO can't be null");
        }
        Date now = new Date();
        ProjectTest projectTest = new ProjectTest();
        projectTest.setTestDesc(projectTestDTO.getTestDesc());
        projectTest.setDateCreate(now);
        projectTest.setDateUpdate(now);
        boolean isSave = projectTestService.save(projectTest);
        if (!isSave){
            throw new BusinessException(RespEnums.SERVER_ERROR.getCode(), "add project_test fail");
        }
    }
}
