# 陪诊系统 API Mock 期望配置

## 📋 接口统计

| 模块 | 接口数量 |
|------|----------|
| 认证模块 | 4 |
| 用户模块 | 5 |
| 就诊人模块 | 5 |
| 服务模块 | 3 |
| 陪诊师模块 | 14 |
| 订单模块 | 13 |
| 收藏模块 | 3 |
| 消息模块 | 4 |
| 聊天模块 | 3 |
| 充值模块 | 3 |
| 钱包模块 | 2 |
| 上传模块 | 1 |
| 字典模块 | 3 |
| 反馈模块 | 2 |
| AI 模块 | 3 |
| **总计** | **68** |

---

## 1️⃣ 认证模块 `/auth`

### POST /auth/register - 用户注册
**参数**: `{ phone, password, verifyCode, nickname }`
```json
{
  "code": 200,
  "msg": "注册成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userInfo": {
      "id": 1,
      "nickname": "张三",
      "avatar": "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
      "phone": "13800138000",
      "balance": 0
    }
  }
}
```

### POST /auth/login - 用户登录
**参数**: `{ phone, password }`
```json
{
  "code": 200,
  "msg": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userInfo": {
      "id": 1,
      "nickname": "张三",
      "avatar": "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
      "phone": "13800138000",
      "balance": 1000.50
    }
  }
}
```

### POST /auth/logout - 用户登出
```json
{
  "code": 200,
  "msg": "登出成功",
  "data": null
}
```

### POST /auth/reset-password - 重置密码
**参数**: `{ phone, verifyCode, newPassword }`
```json
{
  "code": 200,
  "msg": "密码重置成功",
  "data": null
}
```

---

## 2️⃣ 用户模块 `/user`

### GET /user/profile - 获取用户信息
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "id": 1,
    "nickname": "张三",
    "avatar": "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
    "phone": "13800138000",
    "balance": 1000.50
  }
}
```

### PUT /user/profile - 更新用户信息
**参数**: `{ nickname?, avatar?, phone?, ... }`
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

### PUT /user/avatar - 更新用户头像
**参数**: `{ avatar: string }`
```json
{
  "code": 200,
  "msg": "头像更新成功",
  "data": null
}
```

### PUT /user/password - 修改密码
**参数**: `{ oldPassword, newPassword }`
```json
{
  "code": 200,
  "msg": "密码修改成功",
  "data": null
}
```

### GET /user/balance - 获取用户余额
```json
{
  "code": 200,
  "msg": "success",
  "data": 1000.50
}
```

---

## 3️⃣ 就诊人模块 `/user/patients`

### GET /user/patients - 获取就诊人列表
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "name": "李女士",
      "phone": "13900139000",
      "address": "北京市朝阳区",
      "relationship": "母亲",
      "default": true
    },
    {
      "id": 2,
      "name": "王先生",
      "phone": "13800138001",
      "address": "北京市海淀区",
      "relationship": "父亲",
      "default": false
    }
  ]
}
```

### POST /user/patients - 添加就诊人
**参数**: `{ name, phone, address, relationship, default? }`
```json
{
  "code": 200,
  "msg": "添加成功",
  "data": null
}
```

### PUT /user/patients/{id} - 更新就诊人
**参数**: `{ name?, phone?, address?, ... }`
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

### DELETE /user/patients/{id} - 删除就诊人
```json
{
  "code": 200,
  "msg": "删除成功",
  "data": null
}
```

### PUT /user/patients/{id}/default - 设置默认就诊人
```json
{
  "code": 200,
  "msg": "设置成功",
  "data": null
}
```

---

## 4️⃣ 服务模块 `/services`

### GET /services - 获取服务列表
**参数**: `{ type?, sort?, keyword? }`
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "name": "门诊陪诊",
      "description": "专业陪诊师全程陪同就诊",
      "price": 298,
      "type": "companion",
      "sales": 1523,
      "image": "https://example.com/service1.jpg",
      "duration": "4小时"
    },
    {
      "id": 2,
      "name": "全天陪诊",
      "description": "全天候陪诊服务",
      "price": 598,
      "type": "companion",
      "sales": 892,
      "image": "https://example.com/service2.jpg",
      "duration": "8小时"
    },
    {
      "id": 3,
      "name": "代办问诊",
      "description": "专业团队代办问诊，省时省力",
      "price": 158,
      "type": "agency",
      "sales": 654,
      "image": "https://example.com/service3.jpg",
      "duration": "一次"
    },
    {
      "id": 4,
      "name": "尊享VIP陪诊",
      "description": "资深陪诊师一对一服务，专车接送",
      "price": 888,
      "type": "companion",
      "sales": 120,
      "image": "https://example.com/service4.jpg",
      "duration": "8小时"
    },
    {
      "id": 5,
      "name": "孕妇专属陪诊",
      "description": "女性陪诊师贴心服务，产检无忧",
      "price": 358,
      "type": "companion",
      "sales": 432,
      "image": "https://example.com/service5.jpg",
      "duration": "4小时"
    },
    {
      "id": 6,
      "name": "儿童陪诊",
      "description": "耐心细致，熟悉儿科就诊流程",
      "price": 328,
      "type": "companion",
      "sales": 289,
      "image": "https://example.com/service6.jpg",
      "duration": "4小时"
    }
  ]
}
```

### GET /services/{id} - 获取服务详情
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "id": 1,
    "name": "门诊陪诊",
    "description": "专业陪诊师全程陪同就诊，协助挂号、取号、缴费、取药等服务",
    "price": 298,
    "type": "companion",
    "sales": 1523,
    "image": "https://example.com/service1.jpg",
    "duration": "4小时",
    "features": ["协助挂号排队", "陪同就诊检查", "代取检查报告", "代取药品"]
  }
}
```

### GET /services/categories - 获取服务分类
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    { "id": 1, "name": "门诊陪诊", "icon": "https://example.com/icon1.png" },
    { "id": 2, "name": "住院陪护", "icon": "https://example.com/icon2.png" },
    { "id": 3, "name": "异地陪诊", "icon": "https://example.com/icon3.png" }
  ]
}
```

---

## 5️⃣ 陪诊师模块 `/companions`

### GET /companions - 获取陪诊师列表
**参数**: `{ page, size, gender?, service?, sort?, keyword? }`
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "total": 50,
    "list": [
      {
        "id": 101,
        "name": "武海艳",
        "avatar": "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
        "gender": "female",
        "age": 32,
        "score": 4.8,
        "orders": 256,
        "comments": 189,
        "certified": true,
        "qualified": true,
        "experience": "3年",
        "tags": ["耐心", "专业"],
        "intro": "从事陪诊行业3年，服务周到",
        "collected": true
      },
      {
        "id": 102,
        "name": "李强",
        "avatar": "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
        "gender": "male",
        "age": 28,
        "score": 4.9,
        "orders": 512,
        "comments": 420,
        "certified": true,
        "qualified": true,
        "experience": "4年",
        "tags": ["力气大", "跑腿快"],
        "intro": "退伍军人，身体素质好，熟悉各大医院流程",
        "collected": false
      },
      {
        "id": 103,
        "name": "王芳",
        "avatar": "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
        "gender": "female",
        "age": 45,
        "score": 5.0,
        "orders": 890,
        "comments": 800,
        "certified": true,
        "qualified": true,
        "experience": "10年",
        "tags": ["护士背景", "亲和力"],
        "intro": "前三甲医院护士，具备专业护理知识，擅长老年人陪诊",
        "collected": true
      },
      {
        "id": 104,
        "name": "张伟",
        "avatar": "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
        "gender": "male",
        "age": 30,
        "score": 4.7,
        "orders": 150,
        "comments": 120,
        "certified": true,
        "qualified": true,
        "experience": "2年",
        "tags": ["细心", "负责"],
        "intro": "工作认真负责，熟悉挂号缴费流程",
        "collected": false
      },
      {
        "id": 105,
        "name": "陈静",
        "avatar": "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
        "gender": "female",
        "age": 25,
        "score": 4.8,
        "orders": 80,
        "comments": 60,
        "certified": true,
        "qualified": true,
        "experience": "1年",
        "tags": ["温柔", "耐心"],
        "intro": "性格开朗，善于沟通，陪伴就诊不孤单",
        "collected": false
      }
    ],
    "page": 1,
    "size": 10
  }
}
```

### GET /companions/{id} - 获取陪诊师详情
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "id": 101,
    "name": "武海艳",
    "avatar": "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
    "gender": "female",
    "age": 32,
    "score": 4.8,
    "orders": 256,
    "comments": 189,
    "certified": true,
    "qualified": true,
    "experience": "3年",
    "tags": ["耐心", "专业", "细心"],
    "intro": "从事陪诊行业3年，服务周到，深受客户好评",
    "collected": true
  }
}
```

### GET /companions/{id}/comments - 获取陪诊师评价
**参数**: `{ page, size }`
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "total": 189,
    "list": [
      {
        "id": 1,
        "userName": "用户A",
        "userAvatar": "https://example.com/user1.jpg",
        "score": 5,
        "content": "服务非常好，陪诊师很专业",
        "images": ["https://example.com/comment1.jpg"],
        "time": "2024-03-15 10:30"
      }
    ],
    "page": 1,
    "size": 10
  }
}
```

### POST /companions/{id}/favorite - 收藏/取消收藏陪诊师
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

### GET /companions/{id}/available-times - 获取陪诊师可用时间段
**参数**: `{ date }` (格式：YYYY-MM-DD)
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    { "time": "09:00", "status": "available" },
    { "time": "09:30", "status": "available" },
    { "time": "10:00", "status": "booked" },
    { "time": "10:30", "status": "available" },
    { "time": "11:00", "status": "available" },
    { "time": "11:30", "status": "booked" },
    { "time": "14:00", "status": "available" },
    { "time": "14:30", "status": "available" },
    { "time": "15:00", "status": "booked" },
    { "time": "15:30", "status": "available" }
  ]
}
```

---

## 6️⃣ 陪诊师工作台 `/companion`

### GET /companion/statistics - 获取统计数据
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "todayIncome": 598.00,
    "todayOrders": 3,
    "totalIncome": 45680.00,
    "totalOrders": 256,
    "rating": 4.8,
    "followers": 156,
    "workDays": 365
  }
}
```

### GET /companion/profile - 获取个人信息
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "id": 101,
    "name": "武海艳",
    "nickname": "武海艳",
    "avatar": "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
    "age": 32,
    "gender": "female",
    "experience": "3年",
    "phone": "13800138888",
    "intro": "从事陪诊行业3年，服务周到"
  }
}
```

### PUT /companion/profile - 更新个人信息
**参数**: `{ nickname?, avatar?, ... }`
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

### GET /companion/available-orders - 获取可接订单
**参数**: `{ page, size }`
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "total": 30,
    "list": [
      {
        "id": "ORD20240315001",
        "serviceName": "门诊陪诊",
        "hospital": "北京协和医院",
        "department": "心内科",
        "appointmentTime": "2024-03-16 09:00",
        "pickupOption": "需要接送",
        "amount": 298.00,
        "distance": "2.5km",
        "patientName": "李女士"
      }
    ],
    "page": 1,
    "size": 10
  }
}
```

### PUT /companion/orders/{orderId}/accept - 接单
```json
{
  "code": 200,
  "msg": "接单成功",
  "data": {
    "id": "ORD20240315001",
    "status": 2,
    "serviceName": "门诊陪诊",
    "amount": 298.00
  }
}
```

### PUT /companion/orders/{orderId}/reject - 拒单
**参数**: `{ reason? }`
```json
{
  "code": 200,
  "msg": "拒单成功",
  "data": null
}
```

### PUT /companion/orders/{id}/status - 更新订单状态
**参数**: `{ status }`
```json
{
  "code": 200,
  "msg": "状态更新成功",
  "data": null
}
```

### PUT /companion/orders/{id}/start - 开始服务
```json
{
  "code": 200,
  "msg": "服务已开始",
  "data": null
}
```

### PUT /companion/orders/{id}/complete - 完成服务
**参数**: `{ serviceContent, images }`
```json
{
  "code": 200,
  "msg": "服务已完成",
  "data": null
}
```

### GET /companion/orders - 获取订单列表
**参数**: `{ page, size, status? }`
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "total": 45,
    "list": [
      {
        "id": "ORD20240315001",
        "serviceId": 1,
        "serviceName": "门诊陪诊",
        "patientName": "李女士",
        "patientPhone": "13900139000",
        "hospital": "北京协和医院",
        "department": "心内科",
        "appointmentTime": "2024-03-16 09:00",
        "address": "北京市朝阳区",
        "pickupOption": "需要接送",
        "remark": "请按时到达",
        "amount": 298.00,
        "status": 2,
        "createTime": "2024-03-15 14:30"
      }
    ],
    "page": 1,
    "size": 10
  }
}
```

### GET /companion/orders/{id} - 获取订单详情
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "id": "ORD20240315001",
    "serviceId": 1,
    "serviceName": "门诊陪诊",
    "patientName": "李女士",
    "patientPhone": "13900139000",
    "hospital": "北京协和医院",
    "department": "心内科",
    "appointmentTime": "2024-03-16 09:00",
    "address": "北京市朝阳区",
    "pickupOption": "需要接送",
    "remark": "请按时到达",
    "amount": 298.00,
    "status": 2,
    "createTime": "2024-03-15 14:30"
  }
}
```

### GET /companion/income - 获取收入明细
**参数**: `{ page, size }`
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "total": 256,
    "list": [
      {
        "id": 1,
        "orderId": "ORD20240315001",
        "serviceName": "门诊陪诊",
        "amount": 298.00,
        "time": "2024-03-15 14:30",
        "status": "completed"
      }
    ],
    "page": 1,
    "size": 10
  }
}
```

### POST /companion/status - 更新在线状态
**参数**: `{ isOnline }`
```json
{
  "code": 200,
  "msg": "状态更新成功",
  "data": null
}
```

---

## 7️⃣ 订单模块 `/user/orders`

### GET /user/orders - 获取订单列表
**参数**: `{ page, size, status? }`
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "total": 20,
    "list": [
      {
        "id": "ORD20240315001",
        "serviceName": "门诊陪诊",
        "companionName": "武海艳",
        "patientName": "李女士",
        "hospital": "北京协和医院",
        "appointmentTime": "2024-03-16 09:00",
        "amount": 298.00,
        "status": 2,
        "createTime": "2024-03-15 14:30"
      }
    ],
    "page": 1,
    "size": 10
  }
}
```

### POST /user/orders - 创建订单
**参数**: `{ serviceId, patientId, hospital, department, appointmentTime, pickupOption, remarks?, paymentMethod? }`
```json
{
  "code": 200,
  "msg": "订单创建成功",
  "data": null
}
```

### GET /user/orders/{id} - 获取订单详情
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "id": "ORD20240315001",
    "serviceName": "门诊陪诊",
    "companionName": "武海艳",
    "patientName": "李女士",
    "hospital": "北京协和医院",
    "appointmentTime": "2024-03-16 09:00",
    "amount": 298.00,
    "status": 2,
    "serviceId": 1
  }
}
```

### PUT /user/orders/{id}/cancel - 取消订单
```json
{
  "code": 200,
  "msg": "订单已取消",
  "data": null
}
```

### POST /user/orders/{id}/pay - 支付订单
**参数**: `{ paymentMethod }`
```json
{
  "code": 200,
  "msg": "支付成功",
  "data": null
}
```

### PUT /user/orders/{id}/confirm - 确认完成
```json
{
  "code": 200,
  "msg": "服务已完成",
  "data": null
}
```

### POST /user/orders/{id}/refund - 申请退款
**参数**: `{ reason }`
```json
{
  "code": 200,
  "msg": "退款申请已提交",
  "data": null
}
```

### POST /user/orders/{id}/comment - 评价订单
**参数**: `{ score, content }`
```json
{
  "code": 200,
  "msg": "评价成功",
  "data": null
}
```

---

## 8️⃣ 收藏模块 `/user/favorites`

### GET /user/favorites - 获取收藏列表
**参数**: `{ type? }`
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "type": "companion",
      "itemId": 101,
      "name": "武海艳",
      "avatar": "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      "description": "从事陪诊行业3年，服务周到",
      "time": "2024-03-10"
    }
  ]
}
```

### POST /user/favorites - 添加收藏
**参数**: `{ type, itemId }`
```json
{
  "code": 200,
  "msg": "收藏成功",
  "data": null
}
```

### DELETE /user/favorites/{id} - 取消收藏
```json
{
  "code": 200,
  "msg": "取消收藏成功",
  "data": null
}
```

---

## 9️⃣ 消息模块 `/user/messages`

### GET /user/messages - 获取消息会话列表
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": "service",
      "type": "service",
      "name": "在线客服",
      "time": "10:35",
      "preview": "您好，有什么可以帮助您的？",
      "avatar": "https://example.com/service-avatar.png",
      "unreadCount": 3
    },
    {
      "id": "companion",
      "type": "companion",
      "name": "武海艳",
      "time": "昨天",
      "preview": "收到，我会按时到达。",
      "avatar": "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      "unreadCount": 1
    }
  ]
}
```

### GET /user/messages/unread-count - 获取未读消息数
```json
{
  "code": 200,
  "msg": "success",
  "data": 3
}
```

### PUT /user/messages/{id}/read - 标记消息已读
```json
{
  "code": 200,
  "msg": "标记成功",
  "data": null
}
```

### DELETE /user/messages/{id} - 删除消息会话
```json
{
  "code": 200,
  "msg": "删除成功",
  "data": null
}
```

---

## 🔟 聊天模块 `/user/chats`

### GET /user/chats/{type} - 获取聊天消息
**参数**: `{ page, size }`
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "text": "您好，有什么可以帮助您的？",
      "isMe": false,
      "time": "10:30",
      "type": "service"
    },
    {
      "id": 2,
      "text": "我想咨询陪诊服务",
      "isMe": true,
      "time": "10:31",
      "type": "service"
    }
  ]
}
```

### POST /user/chats/{type}/messages - 发送消息
**参数**: `{ text, type }`
```json
{
  "code": 200,
  "msg": "发送成功",
  "data": null
}
```

### DELETE /user/chats/{type} - 删除聊天会话
```json
{
  "code": 200,
  "msg": "删除成功",
  "data": null
}
```

---

## 1️⃣1️⃣ 充值模块 `/user/recharge`

### GET /user/recharge/config - 获取充值配置
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "amounts": [50, 100, 200, 500, 1000],
    "methods": [
      {
        "value": "wechat",
        "name": "微信支付",
        "icon": "https://example.com/wechat-icon.png"
      },
      {
        "value": "alipay",
        "name": "支付宝",
        "icon": "https://example.com/alipay-icon.png"
      }
    ]
  }
}
```

### POST /user/recharge - 创建充值订单
**参数**: `{ amount, method }`
```json
{
  "code": 200,
  "msg": "充值成功",
  "data": null
}
```

### GET /user/recharge/records - 获取充值记录
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "amount": 100.00,
      "method": "wechat",
      "time": "2024-03-15 10:30:00",
      "status": "success"
    }
  ]
}
```

---

## 1️⃣2️⃣ 钱包模块 `/user`

### GET /user/consumption - 获取消费记录
**参数**: `{ page, size }`
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "total": 15,
    "list": [
      {
        "id": 1,
        "type": "order",
        "title": "门诊陪诊服务",
        "amount": -298.00,
        "balance": 702.50,
        "time": "2024-03-15 14:32",
        "orderId": "ORD20240315001"
      }
    ],
    "page": 1,
    "size": 10
  }
}
```

### GET /user/transactions - 获取交易记录
**参数**: `{ page, size }`
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "total": 18,
    "list": [
      {
        "id": 1,
        "type": "recharge",
        "title": "账户充值",
        "amount": 100.00,
        "balance": 1000.50,
        "time": "2024-03-15 10:30"
      }
    ],
    "page": 1,
    "size": 10
  }
}
```

---

## 1️⃣3️⃣ 上传模块 `/upload`

### POST /upload/image - 上传图片
**参数**: `FormData: file`
```json
{
  "code": 200,
  "msg": "上传成功",
  "data": {
    "url": "https://example.com/uploads/20240315/abc123.jpg"
  }
}
```

---

## 1️⃣4️⃣ 字典模块 `/dict`

### GET /dict/service-types - 获取服务类型
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    { "id": 1, "name": "门诊陪诊", "value": "outpatient" },
    { "id": 2, "name": "住院陪护", "value": "inpatient" },
    { "id": 3, "name": "异地陪诊", "value": "remote" },
    { "id": 4, "name": "取送服务", "value": "delivery" }
  ]
}
```

### GET /dict/hospitals - 获取医院列表
**参数**: `{ keyword? }`
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "name": "北京协和医院",
      "address": "北京市东城区帅府园1号",
      "level": "三甲"
    },
    {
      "id": 2,
      "name": "北京同仁医院",
      "address": "北京市东城区东交民巷1号",
      "level": "三甲"
    }
  ]
}
```

### GET /dict/departments - 获取科室列表
**参数**: `{ hospitalId? }`
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    { "id": 1, "name": "心内科", "hospitalId": 1 },
    { "id": 2, "name": "神经内科", "hospitalId": 1 },
    { "id": 3, "name": "呼吸内科", "hospitalId": 1 },
    { "id": 4, "name": "消化内科", "hospitalId": 1 }
  ]
}
```

---

## 1️⃣5️⃣ 反馈模块 `/user/feedback`

### POST /user/feedback - 提交反馈
**参数**: `{ content, images?, contact? }`
```json
{
  "code": 200,
  "msg": "反馈提交成功",
  "data": null
}
```

### GET /user/feedback - 获取反馈列表
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "content": "建议增加夜间陪诊服务",
      "images": [],
      "contact": "13800138000",
      "status": "pending",
      "reply": "",
      "time": "2024-03-15"
    }
  ]
}
```

---

## 1️⃣6️⃣ AI 模块 `/ai`

### GET /ai/chat - 获取AI聊天历史记录
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "text": "您好！我是您的智能陪诊助手，请问有什么可以帮您？",
      "isMe": false,
      "time": "10:00"
    },
    {
      "id": 2,
      "text": "我想咨询陪诊服务",
      "isMe": true,
      "time": "10:01"
    },
    {
      "id": 3,
      "text": "陪诊服务是指专业的陪诊师陪同患者就医的服务，包括协助挂号、陪同就诊、代取报告、代取药品等。",
      "isMe": false,
      "time": "10:01"
    }
  ]
}
```

### POST /ai/chat - AI智能问答
**参数**: `{ message }`
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "reply": "陪诊服务是指专业的陪诊师陪同患者就医的服务，包括协助挂号、陪同就诊、代取报告、代取药品等。",
    "suggestions": [
      "陪诊服务包括哪些内容？",
      "如何预约陪诊服务？",
      "陪诊服务的收费标准是什么？"
    ]
  }
}
```

### DELETE /ai/chat - 清空AI聊天记录
```json
{
  "code": 200,
  "msg": "聊天记录已清空",
  "data": null
}
```

---

## 📝 统一响应格式说明

### 成功响应
```json
{
  "code": 200,
  "msg": "success",
  "data": { ... }
}
```

### 无数据成功响应 (Void)
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

### 错误响应
```json
{
  "code": 400,
  "msg": "错误信息描述",
  "data": null
}
```

### 未授权响应
```json
{
  "code": 401,
  "msg": "登录已过期，请重新登录",
  "data": null
}
```

---

## 🔑 认证说明

所有需要认证的接口都需要在请求头中携带 token：

```
token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```
