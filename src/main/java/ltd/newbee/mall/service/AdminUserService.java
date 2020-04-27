package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.AdminUser;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

public interface AdminUserService {

    AdminUser login(String userName, String password);

    /**
     * 获取用户信息
     *
     * @param loginUserId
     * @return
     */
    AdminUser getUserDetailById(Integer loginUserId);

    /**
     * 修改当前登录用户的密码
     *
     * @param loginUserId
     * @param originalPassword
     * @param newPassword
     * @return
     */
    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);

    /**
     * 修改当前登录用户的名称信息
     *
     * @param loginUserId
     * @param loginUserName
     * @param nickName
     * @return
     */
    Boolean updateName(Integer loginUserId, String loginUserName, String nickName);

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallUsersPage(PageQueryUtil pageUtil);

    /**
     * 后台分页
     *
     * @param ids
     * @return
     */
    Boolean delete(Integer[] ids);

    /**
     * 修改用户信息
     *
     * @param adminUser
     * @return
     */
    String updateAdminUser(AdminUser adminUser);

    /**
     * 新增
     *
     * @param adminUser
     * @return
     */
    String saveAdminUser(AdminUser adminUser);

}
