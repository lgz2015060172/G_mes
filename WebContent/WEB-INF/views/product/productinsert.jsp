<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>计划管理</title>
<!-- jsp动态导入 -->
<%@ include file="/common/backend_common.jsp"%>
<%@ include file="/common/page.jsp"%>
<%@ include file="/template/productListTemplate.jsp" %>
	<script src="productinsert.js"></script> 
</head>
<body class="no-skin" youdao="bind" style="background: white">
	<input id="gritter-light" checked="" type="checkbox"
		class="ace ace-switch ace-switch-5" />
	<div class="page-header">
		<h1>
			材料管理 <small><i class="ace-icon fa fa-angle-double-right"></i>
				创建与查询 </small>
		</h1>
	</div>
	<div class="main-content-inner">
		<div class="col-sm-12">
			<div class="col-xs-12">
				<div class="table-header">
					材料列表&nbsp;&nbsp;
					<!-- <a class="green" href="#"> <i
						class="ace-icon fa fa-plus-circle orange bigger-130 material-add"></i>
					</a> -->
				</div>
				<div class="table-content">
					<form id="materialForm" action="/product/insert.json" method="post">
						<table
							class="table table-striped table-bplaned table-hover dataTable no-footer"
							role="grid">
							<tr>
								<td><label for="productImgid">图号</label></td>
								<td><input id="productImgid" type="text"
									name="productImgid" value=""
									class="text ui-widget-content ui-corner-all"></td>
								<td><label for="productMaterialname">材料名称</label></td>
								<td><input id="productMaterialname" type="text"
									name="productMaterialname" value=""
									class="text ui-widget-content ui-corner-all"></td>
							</tr>
							<tr>
								<td><label for="productMaterialsource">材料来源</label></td>
								<td><select id="productMaterialsource"
									name="productMaterialsource" data-placeholder="选择类型"
									style="width: 150px;">
										<option value="钢材">钢材</option>
										<option value="废料">废料</option>
										<option value="外购件">外购件</option>
										<option value="外协件">外协件</option>
										<option value="钢锭">钢锭</option>
								</select></td>
								<td><label for="productTargetweight">工艺重量</label></td>
								<td><input id="productTargetweight" type="number"
									name="productTargetweight" value=""
									class="text ui-widget-content ui-corner-all"></td>
							</tr>
							<tr>
								<td><label for="productRealweight">投料重量</label></td>
								<td><input id="productRealweight" type="number"
									name="productRealweight" value=""
									class="text ui-widget-content ui-corner-all"></td>
								<td><label for="productLeftweight">剩余重量</label></td>
								<td><input id="productLeftweight" type="number"
									name="productLeftweight" value="0"
									class="text ui-widget-content ui-corner-all"></td>
							</tr>
							<tr>
								<td><label for="productIrontypeweight">锭型</label></td>
								<td><input id="productIrontypeweight" type="number"
									name="productIrontypeweight" value=""
									class="text ui-widget-content ui-corner-all"></td>
								<td><label for="productIrontype">钢锭类别</label></td>
								<td><input id="productIrontype" type="text"
									name="productIrontype" value=""
									class="text ui-widget-content ui-corner-all"></td>
							</tr>
							<tr>
								<td><label for="productStatus">是否启用</label></td>
								<td><select id="productStatus" name="productStatus"
									data-placeholder="状态" style="width: 150px;">
										<option value="0">未启用</option>
										<option value="1">启用</option>
								</select></td>
								<td><label for="productRemark">备注</label></td>
								<td><input id="productRemark" type="text"
									name="productRemark" value=""
									class="text ui-widget-content ui-corner-all"></td>
							</tr>
							<tr>
								<td><label for="counts">批量生成个数</label></td>
								<td><input id="counts" type="number" name="counts"
									value="" class="text ui-widget-content ui-corner-all"></td>
								<td><label for="submit">生成材料</label></td>
								<td><input id="addproduct" type="submit" value="点击按钮"
									class="btn btn-info fa fa-check" /></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		
</body>
</html>