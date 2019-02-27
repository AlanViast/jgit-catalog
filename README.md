### Git Catelog Plugin

该工具生成 `Git提交日志的记录`

### Example

> jgit.jar -d gitProject/ -t 10 # 生成十天内的日志


### 参数列表

```
jgit.jar
    -d [项目目录]
    -t [多少天内的日志]
    -r [按照上一次的release版本]

```

* -t 或者 -r 必须选一个， 默认值是-t 15，即返回15天内提交的内容 -t的优先级高

### TODO

- [x] 项目根目录搜索到 `.git` 文件夹
- [x] 级别顺序问题
- [x] 输出到文件的抽象以及简单实现
- [ ] 实现Markdown文件输出
