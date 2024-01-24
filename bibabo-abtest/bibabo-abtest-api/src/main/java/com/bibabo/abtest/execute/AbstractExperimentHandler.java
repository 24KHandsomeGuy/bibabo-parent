package com.bibabo.abtest.execute;

import com.bibabo.abtest.IDivMethod;
import com.bibabo.abtest.model.AbExperimentMark;

import java.util.HashSet;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 15:29
 * @Description 试验分流处理者抽象类（责任链） 注意责任链是有顺序的，新增的试验，要放在责任链最后
 */
public abstract class AbstractExperimentHandler {

    /**
     * 下一个处理者
     */
    private AbstractExperimentHandler nextHandler;

    /**
     * 处理试验
     *
     * @param request  携带参数
     * @param response 此参数不能为空，这个参数需要返回用户使用
     * @param splitBo  分流业务类，不可为空
     */
    public final void handler(AbExperimentMark request, AbExperimentMark response, IDivMethod splitBo) {
        if (response == null || splitBo == null) {
            //不进行分流处理
            return;
        }
        if (response.getParticipationSet() == null) {
            response.setParticipationSet(new HashSet<>());
        }
        //流过当前管道
        boolean through = through(request, response, splitBo);
        if (through) {
            //成功命中版本，进入上层通道处理
            handler(response);
            return;
        }
        if (nextHandler == null) {
            //到达最后一个处理者返回
            return;
        }
        //下一个管道处理者处理
        nextHandler.handler(request, response, splitBo);
    }

    /**
     * 上层管道处理，只加入响应标记
     *
     * @param response 响应内容
     */
    private void handler(AbExperimentMark response) {
        //流过上层通道
        through(response);
        if (nextHandler != null) {
            //存在下级流过下级通道
            nextHandler.handler(response);
        }
    }

    public AbstractExperimentHandler getNextHandler() {
        return nextHandler;
    }

    public void setNextHandler(AbstractExperimentHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * 流过试验，处理
     *
     * @param request  携带请求参数
     * @param response 携带响应参数
     * @param splitBo  分流处理类
     * @return 是否分流结束
     */
    protected abstract boolean through(AbExperimentMark request, AbExperimentMark response, IDivMethod splitBo);

    /**
     * 上层管道流过，只加入参与标记
     *
     * @param response 响应内容
     */
    protected abstract void through(AbExperimentMark response);
}
