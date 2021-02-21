package com.tingbei.oauth.service.impl;


import com.tingbei.common.entity.OauthUsers;
import com.tingbei.common.repository.OauthUsersRepository;
import com.tingbei.common.util.GenerateUtil;
import com.tingbei.common.vo.ServiceResultVO;
import com.tingbei.common.vo.UsersInfoParamVO;
import com.tingbei.oauth.service.OauthUsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 维护授权用户列表服务类实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OauthUsersServiceImpl implements OauthUsersService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OauthUsersRepository usersRepository;

    @Autowired
    private GenerateUtil generateUtil;


    // 新增
    @Override
    public ServiceResultVO insertUserInfo(UsersInfoParamVO usersInfoParamVO) {
        //检查相同用户名是否已经存在
        OauthUsers oauthUsers = new OauthUsers();
        oauthUsers.setUserName(usersInfoParamVO.getUserName());
        OauthUsers isExist = this.usersRepository.selectOne(oauthUsers);
        if (null != isExist) {
            return this.generateUtil.generageServiceResultVO(false, null, "此用户名在授权中心已存在", null);
        }
        try {
            BeanUtils.copyProperties(usersInfoParamVO, oauthUsers);
            int rows = this.usersRepository.insert(oauthUsers);
            if (rows > 0) {
                return this.generateUtil.generageServiceResultVO(true, null, "插入授权用户信息成功", rows);
            } else {
                return this.generateUtil.generageServiceResultVO(false, null, "插入授权用户信息失败", rows);
            }
        } catch (Exception e) {
            logger.error("插入授权用户信息异常", e);
            return this.generateUtil.generageServiceResultVO(false, null, "插入授权用户信息异常", null);
        }
    }

    //删除
    @Override
    public ServiceResultVO deleteUserInfoByName(String userName) {
        try {
            OauthUsers user = new OauthUsers();
            user.setUserName(userName);
            int rows = this.usersRepository.delete(user);
            if (rows > 0) {
                //有则级联删除用户关系表
//                int refrenceRows = this.userBaseChainInfoClient.cascadeDeleteRefrence(userName);
                if(1 > 0){
                    return this.generateUtil.generageServiceResultVO(true,null,"删除用户以及关系表成功",rows);
                }else {
                    return this.generateUtil.generageServiceResultVO(false,null,"删除用户成功删除用户关系表失败",rows);
                }
            } else {
                return this.generateUtil.generageServiceResultVO(false, null, "删除用户失败", rows);
            }
        } catch (Exception e) {
            logger.error("删除用户异常", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return this.generateUtil.generageServiceResultVO(false, null, "删除用户异常", null);
        }
    }

    //更新
    @Override
    public ServiceResultVO updateUserInfoByName(UsersInfoParamVO usersInfoParamVO) {
        try {
            OauthUsers realUser = new OauthUsers();
            BeanUtils.copyProperties(usersInfoParamVO,realUser);
            OauthUsers user = new OauthUsers();
            user.setUserName(usersInfoParamVO.getUserName());
            OauthUsers result = this.usersRepository.selectOne(user);
            if(null == result){
                return this.generateUtil.generageServiceResultVO(false,null,"需要更新的用户不存在", null);
            }
            realUser.setUserNo(result.getUserNo());
            int rows = this.usersRepository.updateByPrimaryKey(realUser);
            if(rows > 0){
                return this.generateUtil.generageServiceResultVO(true,null,"更新用户成功", rows);
            }else {
                return this.generateUtil.generageServiceResultVO(false,null,"更新用户失败", rows);
            }
        }catch (Exception e){
            logger.error("更新用户信息异常",e);
            return this.generateUtil.generageServiceResultVO(false,null,"更新用户异常", null);
        }
    }

    @Override
    public ServiceResultVO queryUsersInfo(UsersInfoParamVO usersInfoParamVO) {
        try{
            OauthUsers oauthUsers = new OauthUsers();
            BeanUtils.copyProperties(usersInfoParamVO,oauthUsers);
            OauthUsers queryResult = this.usersRepository.selectOne(oauthUsers);
            if(null != queryResult){
                return this.generateUtil.generageServiceResultVO(true,null,"调用查询用户成功", queryResult);
            }else {
                return this.generateUtil.generageServiceResultVO(false,null,"调用查询用户失败", null);
            }
        }catch (Exception e){
            logger.error("查询用户信息异常",e);
            return this.generateUtil.generageServiceResultVO(false,null,"调用查询用户异常", null);
        }
    }
}
