package per.neal.blog.controller;

import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import per.neal.blog.constant.Constants;
import per.neal.blog.entity.TbColumn;
import per.neal.blog.entity.dto.R;
import per.neal.blog.service.ColumnService;

import javax.annotation.Resource;

/**
 * 栏目控制器
 *
 * @author neal
 */
@Api(tags = "栏目控制器")
@RestController
@RequestMapping("/column")
public class ColumnController {

    @ApiModelProperty("栏目service接口")
    @Resource
    private ColumnService columnService;


    @ApiOperation("新建一个栏目")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "成功保存"),
            @ApiResponse(code = Constants.ERR, message = "保存失败，返回异常信息")
    })
    @RequiresRoles(Constants.ADMIN)
    @PostMapping("/save")
    public R save(@ApiParam(name = "column", value = "TbColumn", required = true) @RequestBody TbColumn column) {
        int count = columnService.countByName(column.getColumnName().toLowerCase().trim());
        if (count > 0) {
            return R.err("该栏目名已存在,请重新填写!!!");
        }
        try {
            columnService.saveColumn(column);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("根据ID查找栏目")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "返回所有栏目", response = TbColumn.class)
    })
    @GetMapping("/findColumn")
    public R findColumn(long id) {
        return R.ok(columnService.findById(id));
    }

    @ApiOperation("查询所有的栏目")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "返回所有栏目列表", response = TbColumn.class)
    })
    @GetMapping("/listColumn")
    public R listColumn() {
        return R.ok(columnService.listColumn());
    }

    @ApiOperation("删除栏目")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "删除成功"),
            @ApiResponse(code = Constants.ERR, message = "删除失败")
    })
    @RequiresRoles(Constants.ADMIN)
    @DeleteMapping("/delete")
    public R delete(long id) {
        try {
            columnService.deleteById(id);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("修改栏目")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "修改成功"),
            @ApiResponse(code = Constants.ERR, message = "修改失败")
    })
    @RequiresRoles(Constants.ADMIN)
    @PutMapping("/modifyColumn")
    public R modifyColumn(@ApiParam(name = "column", value = "TbColumn", required = true) @RequestBody TbColumn column) {
        int count = columnService.countByName(column.getColumnName().trim());
        // 修改时，需要注意，修改的名字是否跟以前的名字一样，一样则跳过栏目名检查。
        String name = columnService.findById(column.getId()).getColumnName();
        if (!name.equalsIgnoreCase(column.getColumnName())) {
            if (count > 0) {
                return R.err("该栏目名已存在,请重新填写!!!");
            }
        }
        try {
            columnService.updateColumn(column);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("根据栏目级别查询栏目列表")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "栏目列表", response = TbColumn.class)
    })
    @GetMapping("/listByLevel")
    public R listByLevel(String columnLevel) {
        try {
            return R.ok(columnService.listByLevel(columnLevel));
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("根据父级栏目ID查询所有子集栏目")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "栏目列表", response = TbColumn.class)
    })
    @GetMapping("/listByParent")
    public R listByParent(long parentId) {
        try {
            return R.ok(columnService.listByParentId(parentId));
        } catch (Exception e) {
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("栏目排行榜")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "所有栏目以及栏目文章数")
    })
    @GetMapping("/rankColumn")
    public R rankColumn() {
        return R.ok(columnService.columnRank());
    }

    @ApiOperation("栏目导航栏")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "栏目导航栏")
    })
    @GetMapping("/columnNav")
    public R columnNav(long id) {
        return R.ok(columnService.columnNav(id));
    }
}
