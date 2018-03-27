$(function () {
    $("#jqGrid").jqGrid({
        url: '../menu/list',
        datatype: "json",
        colModel: [
            {label: 'uid', name: 'uid', width: 45, key: true},
            {label: '菜单名称', name: 'name', width: 75},
            {label: 'pinyin', name: 'pinyin', width: 75},
            {label: '是否展示', name: 'isShow', width: 75},
            {label: 'url', name: 'url', width: 75},

        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "list",
            page: "currPage",
            total: "totalPage",
            records: "totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "uid",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            name: null
        },
        showList: true,
        title: null,
        subjectList: {},
        entitys: {},
        menu: {
            parentName: null,
            parentId: 0,
            type: 1,
            orderNum: 0
        }
    },
    methods: {
        loadMenu: function (menuId) {
            //加载菜单树
            $.get("../menu/select", function (r) {

                ztree = $.fn.zTree.init($("#menuTree"), setting, r.result);
                var node = ztree.getNodeByParam("uid", vm.menu.parentId);
                ztree.selectNode(node);
                vm.menu.parentName = node.name;
            })
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            this.getSubjectList();
            vm.menu = {parentName: null, parentId: 0, type: 1, orderNum: 0};
            this.loadMenu();
            vm.entitys = {};
            vm.entitys.subjectId = "";
        },
        update: function () {
            vm.entitys = {};
            var menuId = getSelectedRow();
            if (menuId == null) {
                return;
            }
            if (menuId != null) {
                vm.getMenu(menuId);
            }
            vm.showList = false;
            vm.title = "修改";

            this.getSubjectList();
            this.loadMenu();

        },
        del: function (event) {
            var getMenuIds = getSelectedRows();
            if (getMenuIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../menu/delete",
                    data: JSON.stringify(getMenuIds),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.message);
                        }
                    }
                });
            });
        },
        getMenu: function (menuId) {
            $.get("../menu/info/" + menuId, function (r) {

                vm.entitys = r.result;
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.entitys.uid == null ? "../menu/save" : "../menu/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.entitys),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.message);
                    }
                }
            });
        },

        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name},
                page: page
            }).trigger("reloadGrid");
        },
        menuTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级菜单
                    // vm.menu.parentId = node[0].uid;
                    vm.menu.parentName = node[0].name;

                    vm.entitys.parentId = node[0].uid;
                    layer.close(index);
                }
            });
        },
        getSubjectList: function (event) {
            $.get("../subject/all", function (r) {
                vm.subjectList = r.result;
            });
        }
    }
});