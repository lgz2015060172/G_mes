$(function(){
	var productImgid;
	var productMaterialname;
	var productMaterialsource;
	var productTargetweight;
	var productRealweight;
	var productLeftweight;
	var productIrontypeweight;
	var productIrontype;
	var productStatus;
	var productRemark;
	var counts;
	
	
	$("#addproduct").click(function(){
		productImgid = $("#productImgid").val();
		productMaterialname = $("#productMaterialname").val();
		productMaterialsource = $("#productMaterialsource").val();
		productTargetweight = $("#productTargetweight").val();
		productRealweight = $("#productRealweight").val();
		productLeftweight = $("#productLeftweight").val();
		productIrontypeweight = $("#productIrontypeweight").val();
		productIrontype = $("#productIrontype").val();
		productStatus = $("#productStatus").val();
		productRemark = $("#productRemark").val();
		counts = $("#counts").val();
		
		$.ajax({
			
				url:"/product/insert.json",
				
				data : {
					productImgid : productImgid,
					productMaterialname : productMaterialname,
					productMaterialsource : productMaterialsource,
					productTargetweight : productTargetweight,
					productRealweight : productRealweight,
					productLeftweight : productLeftweight,
					productIrontypeweight : productIrontypeweight,
					productIrontype : productIrontype,
					productStatus : productStatus,
					productRemark : productRemark,
					counts : counts,
				},
				
				type : 'POST',
				success : function(result){
					if(result){
					
					}
				}
				
		});
		
	});
});