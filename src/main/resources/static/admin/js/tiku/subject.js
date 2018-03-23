$(function () {
    $("#jqGrid").jqGrid({
        url: '../subject/list',
        datatype: "json",
        colModel: [			
			{ label: 'uid', name: 'uid', width: 45, key: true },
			{ label: '类型名称', name: 'name', width: 75 }
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
			idKey: "menuId",
			pIdKey: "parentId",
			rootPId: -1
		},
		key: {
			url:"nourl"
		}
	},
	check:{
		enable:true,
		nocheckInherit:true
	}
};
var ztree;
	
var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			name: null
		},
		showList: true,
		title:null,
		subject:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.subject = {};
		},
		update: function () {
			var subjectId = getSelectedRow();
			if(subjectId == null){
				return ;
			}
            if(subjectId != null){
                vm.getSubject(subjectId);
            }
			vm.showList = false;
            vm.title = "修改";
		},
		del: function (event) {
			var subjectIds = getSelectedRows();
			if(subjectIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../subject/delete",
				    data: JSON.stringify(subjectIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.message);
						}
					}
				});
			});
		},
        getSubject: function(subjectId){
            $.get("../subject/info/"+subjectId, function(r){
            	vm.subject = r.result;
            	console.info(r.result);
    		});
		},
		saveOrUpdate: function (event) {
			var url = vm.subject.uid == null ? "../subject/save" : "../subject/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.subject),
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

	    reload: function (event) {
	    	vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'roleName': vm.q.roleName},
                page:page
            }).trigger("reloadGrid");
		}
	}
});