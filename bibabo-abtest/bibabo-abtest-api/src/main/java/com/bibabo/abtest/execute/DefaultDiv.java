package com.bibabo.abtest.execute;

import com.bibabo.abtest.IDivMethod;
import com.bibabo.abtest.model.AbExperiment;
import com.bibabo.abtest.model.AbExperimentConfig;
import com.bibabo.abtest.model.AbExperimentMark;
import com.bibabo.abtest.model.AbVersionConfig;
import org.apache.commons.lang3.RandomUtils;


/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 15:36
 * @Description 默认分流算法, 分层分流算法
 */
public class DefaultDiv implements IDivMethod {

    private int limit;
    private int random;
    private boolean startRandom;


    /**
     * 初始化，上限1000，随机数1000，未开启随机
     */
    public DefaultDiv() {
        this.limit = 1000;
        this.random = 1000;
        this.startRandom = false;
    }

    /**
     * 构造默认随机算法
     *
     * @param totalFlow 总流量数
     */
    public DefaultDiv(int totalFlow) {
        this.limit = totalFlow;
        this.random = totalFlow;
        this.startRandom = false;
    }

    @Override
    public boolean hitDiv(AbExperimentMark request, AbExperimentMark response, AbExperimentConfig experBo) {
        if (this.startRandom) {
            return splitByRandom(response, experBo);
        } else {
            return splitByLimit(request, response, experBo);
        }
    }

    /**
     * 根据上线阈值进行分流
     *
     * @param request  携带的标记
     * @param response 响应的标记
     * @param experBo  试验的业务信息
     * @return 分流结束标记
     */
    private boolean splitByLimit(AbExperimentMark request, AbExperimentMark response, AbExperimentConfig experBo) {
        if (request != null && request.getParticipationSet().contains(experBo.getExperimentId())) {
            //携带参与标记
            if (checkExperimentMark(request.getExperiment(), experBo)) {
                //携带此试验的试验标记，放入试验，分流结束
                response.setExperiment(request.getExperiment());
                return true;
            }
            //降低上限阈值
            limit = limit - experBo.getFlow() * experBo.getVersionConfigs().size();
        } else {
            if (limit < 0) {
                //限制小于0直接返回
                return true;
            }
            //未参与过，开启随机算法
            startRandom = true;
            //生成随机数并分流
            int randomCreate = RandomUtils.nextInt(0, limit);
            AbExperiment split = split(randomCreate, experBo);
            if (split != null) {
                response.setExperiment(split);
                return true;
            }
            //降低随机数
            this.random = randomCreate - experBo.getVersionConfigs().size() * experBo.getFlow();
        }
        return false;
    }

    /**
     * 检查试验参与标记是否符合规范命中
     *
     * @param experiment 试验标记
     * @param experBo    试验对象
     * @return 是否真的命中
     */
    private boolean checkExperimentMark(AbExperiment experiment, AbExperimentConfig experBo) {
        return experiment != null && experiment.getExperimentId().equals(experBo.getExperimentId()) && experiment.getLayerId().equals(experBo.getLayerId()) && experiment.getVersion().getType() <= experBo.getVersionConfigs().size();
    }

    /**
     * 根据随机数分流
     *
     * @param response 响应标记
     * @param experBo  试验信息
     * @return 分流结束标记
     */
    private boolean splitByRandom(AbExperimentMark response, AbExperimentConfig experBo) {
        //已经开始随机算法
        if (random < 0) {
            //随机数小于0 结束后续处理
            return true;
        }
        AbExperiment split = split(random, experBo);
        if (split != null) {
            response.setExperiment(split);
            return true;
        }
        //降低随机数
        random = random - experBo.getVersionConfigs().size() * experBo.getFlow();
        return false;
    }

    private AbExperiment split(int random, AbExperimentConfig experBo) {
        int version = random / experBo.getFlow() + 1;
        if (version > experBo.getVersionConfigs().size()) {
            return null;
        }
        AbVersionConfig versionConfig = experBo.getVersionConfigByFlow(random);
        if (versionConfig == null) return null;
        return new AbExperiment(experBo, versionConfig);
    }
}
