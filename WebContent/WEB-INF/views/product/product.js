$(function() {
	var ids="";
	$(".batchStart-btn").click(function(){
		//拿到当前被选中的input-checkbox
		var checks=$(".batchStart-check:checked");
		if(checks.length!=null&&checks.length>0){
			//拿到被选中的订单号
			//mesorder-id
			$.each(checks,function(i,check){//
//				console.log($(check).closest("tr").data("id")); h5
//				console.log($(check).closest("tr").attr("data-id"));
				var id=$(check).closest("tr").attr("data-id");//拿到check的第一个祖先元素“tr”并且设置值为“data_id”
				ids+=id+"&";//+=会进行雷类型的自动转换。ids=""&""&""
			});
//			console.log(ids.substr(0,ids.length-1));
			//拼装ids
			ids=ids.substr(0,ids.length-1);//从ids的第0个下标开始，取值到ids.length-1;
			//发送ajax请求
			$.ajax({
				url : "/product/productBatchStart.json",
				data : {//左面是数据名称-键，右面是值
					ids:ids
				},
				type : 'POST',
				success : function(result) {//jsondata  jsondata.getData=pageResult  pageResult.getData=list
					loadProductList();//带参数回调
				}
			});
			ids="";//111&122&111&122
		}
	});

	$(".batchStart-th").click(function(){
		var checks=$(".batchStart-check");
		$.each(checks,function(i,input){
			//状态反选
//			console.log($(input).attr("checked"));调试测试
//			var checked=input.checked;
//			console.log(i+"--"+checked);
			//true-false  false-true  使用三目运算符
			input.checked=input.checked==true?false:true;
		});
	});
			//页面开始加载
			//执行分页逻辑
			//定义一些全局变量
			var productMap = {};//准备一个map格式的仓库，等待存储从后台返回过来的数据
			var optionStr;//选项参数
			var pageSize;//页码条数
			var pageNo;//当前页
			var url;//查询url
			var keyword;//关键字
			var search_status;//查询状态
			var fromTime;
			var toTime;

			//加载模板内容进入html
			//01从模板中获取页面布局内容
			//orderListTemplate就是mustache模板的id值
			var productListTemplate = $("#productListTemplate").html();
			//02使用mustache模板加载这串内容
			//只是把准备好的页面模板拿出来，放在解析引擎中，准备让引擎往里面填充数据（渲染视图）
			Mustache.parse(productListTemplate);
			//渲染分页列表
			//调用分页函数
			loadProductList();
			//点击刷新的时候也需要调用分页函数
			$(".research").click(function(e) {
				e.preventDefault();
				$("#productPage .pageNo").val(1);
				loadProductList();
			});
			function loadProductList(urlnew) {
				pageSize = $("#pageSize").val();
				pageNo = $("#productPage .pageNo").val() || 1;
				if (urlnew) {
					url = urlnew;
				} else {
					url = "/product/product.json";   //分页方法的路径
				}
				keyword = $("#keyword").val();
				search_source = $("#search_source").val();
				//发送请求
				$.ajax({
					url : url,
					data : {//左面是数据名称-键，右面是值
						pageNo : pageNo,
						pageSize : pageSize,
						keyword : keyword,
						search_source : search_source,
					},
					type : 'POST',
					success : function(result) {//jsondata  jsondata.getData=pageResult  pageResult.getData=list
						//渲染order列表和页面--列表+分页一起填充数据显示条目
						renderProductListAndPage(result, url);
					}
				});
			}

			//渲染所有的mustache模板页面
			//result中的存储数据，就是一个list<MesOrder>集合,是由service访问数据库后返回给controller的数据模型
			function renderProductListAndPage(result, url) {
				//从数据库返回过来的数据集合result
				if (result.ret) {
					//再次初始化查询条件
					url = "/product/product.json";
					keyword = $("#keyword").val();
					search_source = $("#search_source").val();
					//如果查询到数据库中有符合条件的product列表
					if (result.data.total > 0) {
						//为订单赋值--在对orderlisttemplate模板进行数据填充--视图渲染
//						//Mustache.render({"name":"李四","gender":"男"});
//						//Mustache.render(list=new ArrayList<String>(){"a01","a02"},{"name":"list[i].name","gender":list[i].gender});
						var rendered = Mustache.render(
								productListTemplate,//<script id="orderListTemplate" type="x-tmpl-mustache">
								{
									"productList" : result.data.data,//{{#orderList}}--List-(result.data.data-list<MesOrder>)
									
									"showStatus" : function() {
										return this.productStatus == 1 ? '有效'
												: (this.productStatus == 0 ? '无效'
														: '删除');
									},
									"bold" : function() {
										return function(text, render) {
											var status = render(text);
											if (status == '有效') {
												return "<span class='label label-sm label-success'>有效</span>";
											} else if (status == '无效') {
												return "<span class='label label-sm label-warning'>无效</span>";
											} else {
												return "<span class='label'>删除</span>";
											}
										}
									}
								});
						//把页面上的值传到表单中去
						$.each(result.data.data, function(i, product) {//java-增强for
							product.productCometime = new Date(product.productCometime)
									.Format("yyyy-MM-dd");
							product.productCommittime = new Date(product.productCommittime)
									.Format("yyyy-MM-dd");
							productMap[product.id] = product;//result.data.data等同于List<mesOrder>
						});
						$('#productList').html(rendered);
					} else {
						$('#productList').html('');
					}
					
                    bindProductClick();//调用跟新
					var pageSize = $("#pageSize").val();
					var pageNo = $("#productPage .pageNo").val() || 1;
					//渲染页码
					renderPage(
							url,
							result.data.total,
							pageNo,
							pageSize,
							result.data.total > 0 ? result.data.data.length : 0,
							"productPage", loadProductList);
				} else {
					showMessage("获取订单列表", result.msg, false);
				}
			}
			//新增和修改order的通用方法-dml
		//isCreate是否是新增订单(true,false)，如果不是，执行修改
		//successCallbak function(data)  failCallbak function(data)
		function updateProduct(isCreate, successCallbak, failCallbak) {
			$.ajax({
				url : isCreate ? "/product/product.json"
						: "/product/productUpdate.json",
				data : isCreate ? $("#materialForm").serializeArray() : $(
						"#productUpdateForm").serializeArray(),
				type : 'POST',
				success : function(result) {
					//数据执行成功返回的消息
					if (result.ret) {
	                	loadProductList(); // 带参数回调
						//带参数回调
						if (successCallbak) {
							successCallbak(result);
						}
					} else {
						//执行失败后返回的内容
						if (failCallbak) {
							failCallbak(result);
						}
					}
				}
			});
		}
		//////////////////////////////////////////////////////////////
		function bindProductClick(){
			   $(".product-edit").click(function(e) {
	            e.preventDefault();
	            e.stopPropagation();
	            var productId = $(this).attr("data-id");
	            $("#dialog-productUpdate-form").dialog({
	                model: true,
	                title: "编辑钢材",
	                open: function(event, ui) {
	             	    $(".ui-dialog").css("width","600px");
	                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
	                  	//将form表单中的数据清空，使用jquery转dom对象
	                    $("#productUpdateForm")[0].reset();
	                  	//拿到map中以键值对，id-plan对象结构的对象,用来向form表单中传递数据
	                    var targetOrder = productMap[productId];
	                  	//如果取出这个对象
	                    if (targetOrder) {
							/////////////////////////////////////////////////////////////////
							$("#input-id2").val(targetOrder.id);
							$("#input-productImgid2").val(targetOrder.productImgid);
							$("#input-productMaterialname2").val(targetOrder.productMaterialname);
							$("#input-productMaterialsource2").val(targetOrder.productMaterialsource);
							$("#input-productTargetweight2").val(targetOrder.productTargetweight);
							$("#input-productIrontypeweight2").val(targetOrder.productIrontypeweight);
							$("#input-productIrontype2").val(targetOrder.productIrontype);
							$("#input-productRemark2").val(targetOrder.productRemark);
							$("#input-productRealweight2").val(targetOrder.productRealweight);
							$("#input-productLeftweight2").val(targetOrder.productLeftweight);
							/////////////////////////////////////////////////////////////////
	                    }
	                },
	                buttons : {
	                    "更新": function(e) {
	                        e.preventDefault();
	                        updateProduct(false, function (data) {
	                            $("#dialog-productUpdate-form").dialog("close");
	            				$("#productPage .pageNo").val(1);
	            				loadProductList();
	                        }, function (data) {
	                            showMessage("更新材料", data.msg, false);
	                        })
	                    },
	                    "取消": function (data) {
	                        $("#dialog-productUpdate-form").dialog("close");
	                    }
	                }
	            });
	        });
		   }  
		//////////////////////////////////////////////////////////////
			//日期显示
			$('.datepicker').datepicker({
				dateFormat : 'yy-mm-dd',
				showOtherMonths : true,
				selectOtherMonths : false
			});
});
