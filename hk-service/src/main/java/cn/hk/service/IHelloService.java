package cn.hk.service;

import cn.hk.model.dto.ProjectTestDTO;
import cn.hk.model.vo.ProjectTestVo;

import java.util.List;

public interface IHelloService {
    List<ProjectTestVo> queryAllProjectTest();

    void addOneProjectTestRecord(ProjectTestDTO projectTestDTO);
}
