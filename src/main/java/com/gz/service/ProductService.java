package com.gz.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.gz.beans.PageQuery;
import com.gz.beans.PageResult;
import com.gz.common.JsonData;
import com.gz.dao.MesProductCustomerMapper;
import com.gz.dao.MesProductMapper;
import com.gz.dto.SearchProductDto;
import com.gz.exception.SysMineException;
import com.gz.model.MesProduct;
import com.gz.param.MesProductVo;
import com.gz.param.SearchProductParam;
import com.gz.util.BeanValidator;

@Service
public class ProductService {
	@Resource
	private MesProductMapper MesProductMapper;
	@Resource
	private SqlSession sqlSession;
	@Resource
	private MesProductCustomerMapper MesProductCustomerMapper;
	// 一开始就定义一个id生成器
	private IdGenerator ig = new IdGenerator();

	public void productBatchInserts(MesProductVo mesProductVo) {
		// 数据校验
		BeanValidator.check(mesProductVo);
		// 先去判断是否是批量添加
		Integer counts = mesProductVo.getCounts();
		// 根据counts的个数，生成需要添加的ids的数据集合
		// zx180001 zx180002
		List<String> ids = createOrderIdsDefault(Long.valueOf(counts));
		// sql的批量添加处理
		// MesProductMapper mesProductBatchMapper =
		// sqlSession.getMapper(MesProductMapper.class);
		for (String orderid : ids) {
			try {
				// 将vo转换为po
				MesProduct mesProduct = MesProduct.builder().id(mesProductVo.getId()).pId(mesProductVo.getPId())
						.productId(orderid).productOrderid(mesProductVo.getProductOrderid())
						.productPlanid(mesProductVo.getProductPlanid())
						.productTargetweight(mesProductVo.getProductTargetweight())
						.productRealweight(mesProductVo.getProductRealweight())
						.productLeftweight(mesProductVo.getProductLeftweight())
						.productBakweight(mesProductVo.getProductLeftweight())
						.productIrontype(mesProductVo.getProductIrontype())
						.productIrontypeweight(mesProductVo.getProductIrontypeweight())
						.productMaterialname(mesProductVo.getProductMaterialname())
						.productImgid(mesProductVo.getProductImgid())
						.productMaterialsource(mesProductVo.getProductMaterialsource())
						.productStatus(mesProductVo.getProductStatus()).productRemark(mesProductVo.getProductRemark())
						.luhao(mesProductVo.getLuhao()).build();
				// 设置用户的登录信息
				// TODO
				mesProduct.setProductOperator("xiaofugui");
				mesProduct.setProductOperateIp("127.0.0.1");
				mesProduct.setProductOperateTime(new Date());
				MesProductMapper.insertSelective(mesProduct);
			} catch (Exception e) {
				throw new SysMineException("创建过程有问题");
			}
		}
	}

	// 获取数据库所有的数量 为生成id作准备
	public Long getProductCount() {
		return MesProductCustomerMapper.getproductCount();
	}

	// 获取id集合
	public List<String> createOrderIdsDefault(Long ocounts) {
		if (ig == null) {
			ig = new IdGenerator();
		}
		ig.setCurrentdbidscount(getProductCount());
		List<String> list = ig.initIds(ocounts);
		ig.clear();
		return list;
	}

	// 批量启动
	public void batchStart(String ids) {
		// 144&143--order(id)
		if (ids != null && ids.length() > 0) {
			// 批量处理的sqlSession代理
			String[] idArray = ids.split("&");// 将字符串用&分割返回一个数组
			MesProductCustomerMapper.batchStart(idArray);
		}
	}

	public Object searchPageList(SearchProductParam param, PageQuery page) {
		// 验证页码是否为空
		BeanValidator.check(page);
		// 将param中的字段传入dto进行数据层的交互
		// 自定义的数据模型，用来与数据库进行交互操作
		// searchDto 用于分页的where语句后面
		SearchProductDto dto = new SearchProductDto();
		// copyparam中的值进入dto
		if (StringUtils.isNotBlank(param.getKeyword())) {// 拿到页面上关键词的值
			dto.setKeyword("%" + param.getKeyword() + "%");// 模糊查询
		}
		if (StringUtils.isNotBlank(param.getSearch_source())) {// 拿到页面上材料来源的值
			dto.setSearch_source(param.getSearch_source());// 把页面上的值传入dto去与数据库交互
		}
		dto.setSearch_status(0);
		int count = MesProductCustomerMapper.countBySearchDto(dto);// 通过自定义mapper查询出product_sttus=0的数据个数
		if (count > 0) {
			// 返回数据库查询的结果
			List<MesProduct> productList = MesProductCustomerMapper.getPageListBySearchDto(dto, page);
			return PageResult.<MesProduct>builder().total(count).data(productList).build();
		}

		return PageResult.<MesProduct>builder().build();// 返回一个MesProduct的Pageresult库
	}
       //材料到库分页
	public Object searchComePageList(SearchProductParam param, PageQuery page) {
		// 验证页码是否为空
		BeanValidator.check(page);
		// 将param中的字段传入dto进行数据层的交互
		// 自定义的数据模型，用来与数据库进行交互操作
		// searchDto 用于分页的where语句后面
		SearchProductDto dto = new SearchProductDto();
		// copyparam中的值进入dto
		if (StringUtils.isNotBlank(param.getKeyword())) {// 拿到页面上关键词的值
			dto.setKeyword("%" + param.getKeyword() + "%");// 模糊查询
		}
		if (StringUtils.isNotBlank(param.getSearch_source())) {// 拿到页面上材料来源的值
			dto.setSearch_source(param.getSearch_source());// 把页面上的值传入dto去与数据库交互
		}
		dto.setSearch_status(1);

		int count = MesProductCustomerMapper.countBySearchDto(dto);// 通过自定义mapper查询出product_sttus=0的数据个数
		if (count > 0) {
			// 返回数据库查询的结果
			List<MesProduct> productList = MesProductCustomerMapper.getPageListBySearchDto(dto, page);
			return PageResult.<MesProduct>builder().total(count).data(productList).build();
		}

		return PageResult.<MesProduct>builder().build();// 返回一个MesProduct的Pageresult库
	}

	// 钢锭的分页

	public Object searchIronPageList(SearchProductParam param, PageQuery page) {
		// 验证页码是否为空
		BeanValidator.check(page);
		// 将param中的字段传入dto进行数据层的交互
		// 自定义的数据模型，用来与数据库进行交互操作
		// searchDto 用于分页的where语句后面
		SearchProductDto dto = new SearchProductDto();
		// copyparam中的值进入dto
		if (StringUtils.isNotBlank(param.getKeyword())) {// 拿到页面上关键词的值
			dto.setKeyword("%" + param.getKeyword() + "%");// 模糊查询
		}
		if (StringUtils.isNotBlank(param.getSearch_source())) {// 拿到页面上材料来源的值
			dto.setSearch_source(param.getSearch_source());// 把页面上的值传入dto去与数据库交互
		}
		int count = MesProductCustomerMapper.countBySearchDto(dto);// 通过自定义mapper查询出product_sttus=0的数据个数
		if (count > 0) {
			// 返回数据库查询的结果
			List<MesProduct> productList = MesProductCustomerMapper.getPageListBySearchIronDto(dto, page);
			return PageResult.<MesProduct>builder().total(count).data(productList).build();
		}

		return PageResult.<MesProduct>builder().build();// 返回一个MesProduct的Pageresult库
	}
/////////////////////////////////////////////////////////////////
	//分页查询初绑定界面searchBindPageList
	public Object searchBindPageList(SearchProductParam param, PageQuery page) {
		BeanValidator.check(page);
		SearchProductDto dto = new SearchProductDto();
		if (StringUtils.isNotBlank(param.getKeyword())) {// 拿到页面上关键词的值
			dto.setKeyword("%" + param.getKeyword() + "%");// 模糊查询
		}
		if (StringUtils.isNotBlank(param.getSearch_source())) {// 拿到页面上材料来源的值
			dto.setSearch_source(param.getSearch_source());// 把页面上的值传入dto去与数据库交互
		}
		dto.setSearch_status(1);
		int count = MesProductCustomerMapper.countBySearchBindDto(dto);// 通过自定义mapper查询出product_sttus=1的数据个数
		if (count > 0) {
			// 返回数据库查询的结果
			List<MesProduct> productList = MesProductCustomerMapper.getPageListBySearchBindDto(dto, page);
			return PageResult.<MesProduct>builder().total(count).data(productList).build();
		}

		return PageResult.<MesProduct>builder().build();// 返回一个MesProduct的Pageresult库
	}
	
	// 1 默认生成代码
	// 2 手工生成代码
	// id生成器
	class IdGenerator {
		// 数量起始位置
		private Long currentdbidscount;
		private List<String> ids = new ArrayList<String>();
		private String idpre;
		private String yearstr;
		private String idafter;

		public IdGenerator() {
		}

		public Long getCurrentdbidscount() {
			return currentdbidscount;
		}

		public void setCurrentdbidscount(Long currentdbidscount) {
			this.currentdbidscount = currentdbidscount;
			if (null == this.ids) {
				this.ids = new ArrayList<String>();
			}
		}

		public List<String> getIds() {
			return ids;
		}

		public void setIds(List<String> ids) {
			this.ids = ids;
		}

		public String getIdpre() {
			return idpre;
		}

		public void setIdpre(String idpre) {
			this.idpre = idpre;
		}

		public String getYearstr() {
			return yearstr;
		}

		public void setYearstr(String yearstr) {
			this.yearstr = yearstr;
		}

		public String getIdafter() {
			return idafter;
		}

		public void setIdafter(String idafter) {
			this.idafter = idafter;
		}

		public List<String> initIds(Long ocounts) {
			for (int i = 0; i < ocounts; i++) {
				this.ids.add(getIdPre() + getIdAfter(i));
			}
			return this.ids;
		}

		//
		private String getIdAfter(int addcount) {
			// 系统默认生成5位 ZX1700001
			int goallength = 6;
			// 获取数据库order的总数量+1+循环次数(addcount)
			int count = this.currentdbidscount.intValue() + 1 + addcount;
			StringBuilder sBuilder = new StringBuilder("");
			// 计算与5位数的差值
			int length = goallength - new String(count + "").length();
			for (int i = 0; i < length; i++) {
				sBuilder.append("0");
			}
			sBuilder.append(count + "");
			return sBuilder.toString();
		}

		private String getIdPre() {
			// idpre==null?this.idpre="ZX":this.idpre=idpre;
			this.idpre = "gz_x_";
			return this.idpre;
		}

		public void clear() {
			this.ids = null;
		}

		@Override
		public String toString() {
			return "IdGenerator [ids=" + ids + "]";
		}
	}
/// 根据id查询当前的mesproduct
	public MesProduct selectOneById(Integer iD) {
		MesProduct  mesProduct=	MesProductMapper.selectByPrimaryKey(iD);
		return mesProduct;
	}

	public void update(MesProductVo mesProductVo) {
		// 校验
		BeanValidator.check(mesProductVo);
		
		MesProduct product=MesProductMapper.selectByPrimaryKey(mesProductVo.getId());
		product.setProductImgid(mesProductVo.getProductImgid());
		product.setProductIrontype(mesProductVo.getProductIrontype());
		product.setProductIrontypeweight(mesProductVo.getProductIrontypeweight());
		product.setProductMaterialname(mesProductVo.getProductMaterialname());
		product.setProductTargetweight(mesProductVo.getProductTargetweight());
		product.setProductMaterialsource(mesProductVo.getProductMaterialsource());
		product.setProductRemark(mesProductVo.getProductRemark());
		product.setProductRealweight(mesProductVo.getProductRealweight());
		
		
		product.setProductLeftweight(mesProductVo.getProductLeftweight());
		//剩余重量备份需要重新设置
		product.setProductBakweight(mesProductVo.getProductLeftweight());
		
		MesProductMapper.updateByPrimaryKeySelective(product);
	}

	
	//查询可以绑定的子材料的分页
	public Object searchChildBindPageList(SearchProductParam param, PageQuery page) {
		 
		BeanValidator.check(page);
		SearchProductDto dto = new SearchProductDto();
		dto.setSearch_status(0);
		int count = MesProductCustomerMapper.countBySearchBindChildDto(dto);// 通过自定义mapper查询出product_sttus=1的数据个数
		if (count > 0) {
			// 返回数据库查询的结果
			List<MesProduct> productList = MesProductCustomerMapper.getPageListBySearchBindChildDto(dto, page);
			return PageResult.<MesProduct>builder().total(count).data(productList).build();
		}

		return PageResult.<MesProduct>builder().build();// 返回一个MesProduct的Pageresult库
		
	}

///////////////////////绑定逻辑
		public void bind(HttpSession session, String childId) {
			 MesProduct m1=(MesProduct) session.getAttribute("product");
			Integer pid=m1.getId();
			Integer cid=Integer.parseInt(childId);
			
			MesProduct parent=MesProductMapper.selectByPrimaryKey(pid);
			MesProduct child=MesProductMapper.selectByPrimaryKey(cid);
			
			//钢材的理论剩余和真实剩余比钢锭的工艺重量大或等
			//后台的健壮性判断，防止页面js效果失效
			if(parent.getProductLeftweight()>=parent.getProductBakweight()&&parent.getProductBakweight()>=child.getProductTargetweight()) {
				//计算钢材的剩余理论重量剩余值
				parent.setProductBakweight(parent.getProductBakweight()-child.getProductTargetweight());
				//计算钢锭的理论重量剩余值，修改status为真，绑定pid
				child.setpId(pid);
				child.setProductBakweight(child.getProductTargetweight());
				child.setProductStatus(1);
				//更新parent与child  钢材 与 钢锭
				MesProductMapper.updateByPrimaryKeySelective(parent);
				MesProductMapper.updateByPrimaryKeySelective(child);
			}
		}
//绑定了的父级材料的界面searchBindSecondPageList
		public Object searchBindSecondPageList(SearchProductParam param, PageQuery page) {
			 
			BeanValidator.check(page);
			SearchProductDto dto = new SearchProductDto();
			
			int count = MesProductCustomerMapper.countBySearchBindSecondDto(dto);// 通过自定义mapper查询出product_sttus=1的数据个数
			if (count > 0) {
				// 返回数据库查询的结果
				List<MesProduct> productList = MesProductCustomerMapper.getPageListBySearchSecondBindDto(dto);
				return PageResult.<MesProduct>builder().total(count).data(productList).build();
			}

			return PageResult.<MesProduct>builder().build();// 返回一个MesProduct的Pageresult库
			
		}
		
}
