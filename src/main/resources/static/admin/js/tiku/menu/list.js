$(function () {
    $("#jqGrid").jqGrid({
        url: mainHttp + 'admin/menu/list.html',
        datatype: "json",
        colModel: [			
			{ label: '菜单ID', name: 'uid', width: 40, key: true },
			{ label: '菜单名称', name: 'name', width: 60 },
			{ label: '上级菜单', name: 'parentId', width: 60 },
			{ label: '是否显示', name: 'isShow', width: 60 },
			{ label: '拼音', name: 'pinyin', width: 60 },
			{ label: '菜单URL', name: 'url', width: 100 },
			{ label: '类型', name: 'type', width: 50, formatter: function(value, options, row){
				if(value === "0"){
					return '<span class="label label-primary">头部菜单</span>';
				}
				if(value === "1"){
					return '<span class="label label-success">左侧菜单</span>';
				}
				if(value === "2"){
					return '<span class="label label-warning">资格考试专区</span>';
				}
			}},
			{ label: '排序号', name: 'orders', width: 50}                   
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "list",
            page: "currPage",
            total: "totalPage",
            records: "totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
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
			url:"nourl"
		}
	}
};
var ztree;
var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		menu:{
			parentName:null,
			parentId:0,
			type:1,
			orderNum:0
		}
	},
	methods: {
		getMenu: function(menuId){
			//加载菜单树
			$.get(mainHttp + "admin/menu/getMenuTree.html", function(r){
				ztree = $.fn.zTree.init($("#menuTree"), setting, r.result);
				var node = ztree.getNodeByParam("menuId", vm.menu.parentId);
				ztree.selectNode(node);
				vm.menu.parentName = node.name;
			})
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.menu = {parentName:null,parentId:0,type:1,orderNum:0};
			vm.getMenu();
		},
		update: function (event) {
			var menuId = getSelectedRow();
			if(menuId == null){
				return ;
			}
			
			$.get("../sysMenu/info/"+menuId, function(r){
				vm.showList = false;
                vm.title = "修改";
                vm.menu = r.result;
            });
			
			vm.getMenu();
		},
		del: function (event) {
			var menuIds = getSelectedRows();
			if(menuIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sysMenu/delete",
				    data: JSON.stringify(menuIds),
				    success: function(r){
				    	if(r.code === 0){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.message);
						}
					}
				});
			});
		},
		saveOrUpdate: function (event) {
			var url = vm.menu.menuId == null ? "../sysMenu/save" : "../sysMenu/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.menu),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.message);
					}
				}
			});
		},
		menuTree: function(){
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
					vm.menu.parentId = node[0].menuId;
					vm.menu.parentName = node[0].name;
					
					layer.close(index);
	            }
			});
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});