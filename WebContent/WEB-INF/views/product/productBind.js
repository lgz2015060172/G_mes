$(function() {

			//页面开始加载
			//执行分页逻辑
			//定义一些全局变量
			var productMap = {};//准备一个map格式的仓库，等待存储从后台返回过来的数据
			var optionStr;//选项参数
			var pageSize;//页码条数
			var pageNo;//当前页
			var url;//查询url
			var keyword;//关键字
			var productBindTemplate = $("#productBindTemplate").html();
			Mustache.parse(productBindTemplate);
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
					url = "/product/searchChildBind.json";   //分页方法的路径
				}
				search_source = $("#search_source").val();
				//发送请求
				$.ajax({
					url : url,
					data : {//左面是数据名称-键，右面是值
						pageNo : pageNo,
						pageSize : pageSize,
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
					url = "/product/searchChildBind.json";
					search_source = $("#search_source").val();
					//如果查询到数据库中有符合条件的product列表
					if (result.data.total > 0) {
						var rendered = Mustache.render(
								productBindTemplate,//<script id="orderListTemplate" type="x-tmpl-mustache">
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
					
						$('#productList').html(rendered);
					} else {
						$('#productList').html('');
					}
					ProductBind();
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
	///////////////////////////////////////////////////////////////////
			//子材料绑定父级材料
			
			function ProductBind(){
				$(".product-bind").click(function(){
					
					  var childId = $(this).closest("tr").attr("data-id");
				        $.ajax({
				        	url:"/product/productbindSecond.json",
				        	data:{
				        		         "childId":childId
				        		         },
				        	type:"POST",
				        	success:function(result){
				        			if(result.ret){
				        				loadProductList();//绑定之后调用分页函数
				        				/*window.location.href="/product/productbind.page";*/
				        			}
				        		}
				        });
				
				});
				
			}
				
			////////////////////////////////////////////////////////
			
			/////////////////////
			var productBoundMap = {};//准备一个map格式的仓库，等待存储从后台返回过来的数据
			var optionBoundStr;//选项参数
			var boundUrl;//查询url
			var keywordBound;//关键字

			//加载模板内容进入html
			//01从模板中获取页面布局内容
			//planListTemplate就是mustache模板的id值
			var productBoundTemplate = $("#productBoundTemplate").html();
			//02使用mustache模板加载这串内容
			//只是把准备好的页面模板拿出来，放在解析引擎中，准备让引擎往里面填充数据（渲染视图）
			Mustache.parse(productBoundTemplate);
			//渲染分页列表
			//调用分页函数
			loadProductBoundList();
			//定义调用分页函数，一定是当前的查询条件下（keyword，search_status。。）的分页
			function loadProductBoundList(urlnew) {
				if (urlnew) {
					boundUrl = urlnew;
				} else {
					boundUrl = "/product/searchBinded.json";
				}
				var parentId=$(".bind-id").val();
				//发送请求
				$.ajax({
					url : boundUrl,
					data : {//左面是数据名称-键，右面是值
						"pageNo" : 1,
						"pageSize" : 100,
						"pid":parentId
					},
					type : 'POST',
					success : function(result) {//jsondata  jsondata.getData=pageResult  pageResult.getData=list
						//渲染plan列表和页面--列表+分页一起填充数据显示条目
						renderProductBoundListAndPage(result, url);
					}
				});
			}

			//渲染所有的mustache模板页面
			//result中的存储数据，就是一个list<MesOrder>集合,是由service访问数据库后返回给controller的数据模型
			function renderProductBoundListAndPage(result, url) {
				//从数据库返回过来的数据集合result
				if (result.ret) {
					//再次初始化查询条件
					url ="/product/searchBinded.json";
					//如果查询到数据库中有符合条件的plan列表
					if (result.data.total > 0) {
						//为订单赋值--在对planlisttemplate模板进行数据填充--视图渲染
//						Mustache.render({"name":"李四","gender":"男"});
//						Mustache.render(list=new ArrayList<String>(){"a01","a02"},{"name":"list[i].name","gender":list[i].gender});
						var rendered = Mustache.render(
								productBoundTemplate,//<script id="planListTemplate" type="x-tmpl-mustache">
										{
											"productBoundList" : result.data.data,//{{#planList}}--List-(result.data.data-list<MesOrder>)
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
						
						$.each(result.data.data, function(i, product) {//java-增强for
							productBoundMap[product.id] = product;//result.data.data等同于List<mesOrder>
						});
						$('#productBoundList').html(rendered);
					} else {
						$('#productBoundList').html('');
					}
                    bindProductBoundClick();//更新操作
				} else {
					showMessage("获取订单列表", result.msg, false);
				}
			}
			//////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////
			function bindProductBoundClick(){
				   $(".bound-edit").click(function(e) {
					//阻止默认事件
		            e.preventDefault();
					//阻止事件传播
		            e.stopPropagation();
		            var childId = $(this).attr("data-id");
		            var parentId=$(".bind-id").val();
		          //发送请求
					$.ajax({
						url : "/product/parentUnbound.json",
						data : {//左面是数据名称-键，右面是值
							"childId":childId
						},
						type : 'POST',
						success : function(result) {
							if(result.ret){
								window.location.href="/product/productBind.page?id="+parentId;
							}else{
								alert("解除绑定失败");
							}
						}
					});
		        });
			   }  
		//////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////
			//日期显示
			$('.datepicker').datepicker({
				dateFormat : 'yy-mm-dd',
				showOtherMonths : true,
				selectOtherMonths : false
			});
});
