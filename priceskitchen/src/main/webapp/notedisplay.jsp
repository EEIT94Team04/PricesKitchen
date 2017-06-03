<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<script src="js/jquery-1.12.3.min.js"></script>
	<script src="js/jquery-ui.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<title>Insert title here</title>
</head>
<body>
 	<div class="col-md-9">	
 	<div class="jumbotron">

	<table id="productTable" class="table table-bordered">
		<thead>
			<tr>
				<th>購買日期</th>
				<th>食材名稱</th>
				<th>購買數量</th>
				<th>總金額</th>
				<th>單價</th>
				<th>保存期限</th>
				<th>備註</th>
				<th>編輯</th>
			</tr>
		</thead>
		<tbody>

		</tbody>
		<tfoot>
			<tr>
				<form name="myForm" method="post">
					<td>
						<input type="text" style="width: 100px" class="form-control" id="date" name="date" placeholder="購買日期">
					</td>
					<td>
						<input type="text" class="form-control" id="ingre_name" name="ingre_name" placeholder="食材名稱">
					</td>
					<td>
						<input type="text" style="width: 100px" class="form-control" id="quantity" name="quantity" placeholder="購買數量">
					</td>
					<td>
						<input type="text" style="width: 100px" class="form-control" id="total" name="total" placeholder="總金額">
					</td>
					<td>
						<input type="text" style="width: 100px" class="form-control" id="price_each" name="price_each" placeholder="單價">
					</td>
					<td>
						<input type="text" style="width: 100px" class="form-control" id="expire_date" name="expire_date" placeholder="保存期限">
					</td>
					<td>
						<input type="text" style="width: 100px" class="form-control" id="comment" name="comment" placeholder="備註">
					</td>
					<td>
						<button id="buttonAdd" name="add" type="button" class="btn btn-primary">
							<span class="glyphicon glyphicon-ok"></span>
						</button>
						<button id="buttonUpdate" type="button" class="btn btn-success">
							<span class="glyphicon glyphicon-edit"></span>
						</button></td>
				</form>
			</tr>
		</tfoot>
	</table>
		</div>
		    </div>

	<script>
		$(function() {
			loadProduct(1);

			//新增產品
			$('#buttonAdd').click(function() {
				var frm = $('form[name="myForm"]');
				console.log(frm);
				$.ajax({
					'type' : 'post',
					'url' : 'ProductsInsert',
					'data' : frm.serialize()
				}).done(function(data) {
					alert(data);
					loadProduct(1);
					$('#ProductID').val('');
					$('#ProductName').val('');
					$('#UnitPrice').val('');
					$('#UnitsInStock').val('');
				});

			})

			//讀取產品
		 function loadProduct(id){
		    	$.getJSON('Products', {categoryID:id}, function(datas){
					var tb = $('#productTable>tbody');
					var docFrag = $(document.createDocumentFragment());
// 					tb.empty();
					$.each(datas, function(idx, product){
						var cell1 = $('<td></td>').text(product.ProductID);
						var cell2 = $('<td></td>').text(product.ProductName);
						var cell3 = $('<td></td>').text(product.UnitPrice);
						var cell4 = $('<td></td>').text(product.UnitsInStock);
						var cell5 = $('<td></td>').html('<button class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span></button> <button class="btn btn-info"><span class="glyphicon glyphicon-pencil"></span></button>');
						var row = $('<tr></tr>').append([cell1,cell2,cell3,cell4,cell5]);
						
						docFrag.append(row);
					})
					tb.html(docFrag);
				})
			}

			//刪除產品
			$('#productTable>tbody').on(
					'click',
					'button:nth-child(1)',
					function() {
						// 				console.log(this);
						var id = $(this).parents('tr').children('td:eq(0)')
								.text()
						console.log(id);
						var frm = $('form[name="myForm"]');
						$.ajax({
							'type' : 'get',
							'url' : 'ProductsDelete',
							'data' : {
								'ProductID' : id
							}
						}).done(function(data) {
							alert(data);
							loadProduct(1);
						});

					});

			//修改產品
			$('#productTable>tbody').on(
					'click',
					'button:nth-child(2)',
					function() {
						// 				console.log(this);
						var id = $(this).parents('tr').children('td:eq(0)')
								.text();
						$('#ProductID').val(id).next('span').text(id);
						$('#ProductName').val(
								$(this).parents('tr').children('td:eq(1)')
										.text());
						$('#UnitPrice').val(
								$(this).parents('tr').children('td:eq(2)')
										.text());
						$('#UnitsInStock').val(
								$(this).parents('tr').children('td:eq(3)')
										.text());
					});


			//更新產品
			$('#buttonUpdate').click(function() {
				var frm = $('form[name="myForm"]');
				$.ajax({
					'type' : 'post',
					'url' : 'ProductsUpdate',
					'data' : frm.serialize()
				}).done(function(data) {
					alert(data);
					loadProduct(1);
					$('#ProductID').next('span').text('').val('');
					$('#ProductName').val('');
					$('#UnitPrice').val('');
					$('#UnitsInStock').val('');
				});
			});
		})
	</script>
</body>
</html>