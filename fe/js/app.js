

(function (window) {
	'use strict';

	function removeItemAll(){
		$(".item").remove();
	}
	// 할일 갯수를  가져오는 함수
	// 호출 횟수가 많아서 따로 함수로 정의
	function getCount(){

		$.ajax({
			url:'api/todos/count',
			type:'GET',
			contentType: 'application/json; charset=utf8',
			dataType:'json',
			success:function(data){
				var itmeNum = data+' item left';
				$(".item-num").text(itmeNum);
			}
		});
	}

	// 사이트를 방문하거나 새로고침 했읋때 할일 리스트 전체 를 보여주는 함수.
	function refresh(){
			
		$.ajax({
			 url:'/api/todos',
	         type:'GET',
	         contentType: "application/json; charset=utf-8",
	         dataType:'json',
	         success :function(data){
	       
	         	for(var i = 0; i<data.length; ++i){
	         		var dt = new Date(data[i].date);
	         		var month = dt.getMonth()+1;
					var day = dt.getDate();
					var year = dt.getFullYear();
					var date = year + '.' + month + '.' + day;
					var checking;
					var src ;
					if(data[i].completed == 1){
						checking = "check";
						src="image/check.png";
					}
					else{
						checking = "nocheck";
						src="image/nocheck.png";
					}

	         		var item = "<li id=\""+data[i].id+"\" class=\"item\"><div class=\"complete-btn list-item\"> <img class=\""+checking+"\" src=\""+src+"\" width=\"35px;\"></div><div class=\"todo list-item\">"+data[i].todo+"</div><div class=\"date list-item\">"+date+"<div class=\"delete\"><a class=\"delete-item\" href=\"#\">X</a><span class=\"row-date\">"+data[i].date+"</span></div></div></li>";
	         		$(".items").append(item);
					
					if(data[i].completed == 1){
						$("#"+data[i].id+"").find(".todo").css({
								"text-decoration":"line-through",
								"color" : "#d9d9d9"
						});
					}
	         		connectEvent(data[i].id);

	         	}
	         	getCount();
	        }
		});
	}

	// 사로 생성한 요소에 이벤트추가 함수 
	// 호출 횟수가 많아서 따로 함수로 정의 
	function connectEvent(id){


		// 마우스 올렸을때 
		var thisel = $(this);
		$("#"+id+"").mouseenter(function(){
			 $(this).find(".delete").css("display", "block");
		});
		// 마우스가 나갔을때 
		$("#"+id+"").mouseleave(function(){
			$(this).find(".delete").css("display", "none");
		});

		// x 버튼을 클릭했을때 db와 요소 삭제 
		$("#"+id+"").find(".delete-item").click(function(event){
			event.preventDefault();
			$.ajax({
				 url:'/api/todos/'+id,
		         type:'DELETE',
		         contentType: "application/json; charset=utf-8",
		         dataType:'json',
		         success :function(data){
		         	$("#"+id+"").remove();
		         	getCount();
				}
			});
		});

		// complete 버튼을 눌렀을때 
		$("#"+id+"").find("img").click(function(event){
			if($(this).attr("class") == "nocheck"){


				var reqData = {
					"todo" : $("#"+id+"").find('.todo').html(),
					"completed" : "true",
					"date" : $("#"+id+"").find('.row-date').html()
				};

				$.ajax({
					url:'/api/todos/'+id,
		         	type:'PUT',
		         	contentType: "application/json; charset=utf-8",
		         	dataType:'json',
		         	data:JSON.stringify(reqData),
		         	success : function(data){
		         		
		         		$("#"+id+"").find("img").attr({
							"class": "check",
							"src":"image/check.png"
						});
						
						$("#"+id+"").find(".todo").css({
							"text-decoration":"line-through",
							"color" : "#d9d9d9"
						});
						getCount();
				    }
				});
			}

			else{

				var reqData = {
					"todo" : $("#"+id+"").find('.todo').html(),
					"completed" : "false",
					"date" : $("#"+id+"").find('.row-date').html()
				};
				
				$.ajax({
					url:'/api/todos/'+id,
		         	type:'PUT',
		         	contentType: "application/json; charset=utf-8",
		         	dataType:'json',
		         	data:JSON.stringify(reqData),
		         	success : function(data){
		         		$("#"+id+"").find("img").attr({
							"class": "nocheck",
							"src":"image/nocheck.png"
						});
						$("#"+id+"").find(".todo").css({
							"text-decoration":"none",
							"color":"#000"
						});
						getCount();
				    }
				});	
			}
		});
	}


	refresh();
	// 사용자가 할일을 등록헀을때 
	$(".input-form").submit(function(event){
		event.preventDefault();
		var input = $(".input").val(); 
		
		if(input.trim() == ""){
			alert('공백입니다.');
			return;
		}

		$.ajax({
            url:'/api/todos',
            type:'POST',
            contentType: "application/json; charset=utf-8",
            dataType:'json',
            data:$(".input").val(),
            success:function(data){
            	var dt = new Date(data.date);
         		var month = dt.getMonth()+1;
				var day = dt.getDate();
				var year = dt.getFullYear();
				var date = year + '.' + month + '.' + day;

            	var item = "<li id=\""+data.id+"\" class=\"item\"><div class=\"complete-btn list-item\"> <img class=\"nocheck\" src=\"image/nocheck.png\" width=\"35px;\"></div><div class=\"todo list-item\">"+data.todo+"</div><div class=\"date list-item\">"+date+"<div class=\"delete\"><a class=\"delete-item\" href=\"#\">X</a><span class=\"row-date\">"+data.date+"</span></div></div></li>";
		    	$(".items").prepend(item);
				$(".input").val("");
				getCount();	
				connectEvent(data.id);
            }
                
        });
	});

	//all 눌렀을때 이벤트 
	$(".all").click(function(event){
		removeItemAll();
		refresh();
	});
	//active 를 눌렀을때 이벤트 
	$(".active").click(function(event){
		$.ajax({
			url:'api/todos/active',
			type:'GET',
			contentType: 'application/json; charset=utf8',
			dataType:'json',
			success:function(data){
				removeItemAll();
				for(var i =0; i<data.length; ++i){
					var dt = new Date(data[i].date);
	         		var month = dt.getMonth()+1;
					var day = dt.getDate();
					var year = dt.getFullYear();
					var date = year + '.' + month + '.' + day;
						
					var item = "<li id=\""+data[i].id+"\" class=\"item\"><div class=\"complete-btn list-item\"> <img class=\"nocheck\" src=\"image/nocheck.png\" width=\"35px;\"></div><div class=\"todo list-item\">"+data[i].todo+"</div><div class=\"date list-item\">"+date+"<div class=\"delete\"><a class=\"delete-item\" href=\"#\">X</a><span class=\"row-date\">"+data[i].date+"</span></div></div></li>";
	         		$(".items").append(item);
					connectEvent(data[i].id);
				}

			}
		});
	});
	// completed 눌렀을때 이벤트 
	$(".completed").click(function(event){
		$.ajax({
			url:'api/todos/completed',
			type:'GET',
			contentType: 'application/json; charset=utf8',
			dataType:'json',
			success:function(data){
				removeItemAll();
				for(var i =0; i<data.length; ++i){
					var dt = new Date(data[i].date);
	         		var month = dt.getMonth()+1;
					var day = dt.getDate();
					var year = dt.getFullYear();
					var date = year + '.' + month + '.' + day;
						
					var item = "<li id=\""+data[i].id+"\" class=\"item\"><div class=\"complete-btn list-item\"> <img class=\"check\" src=\"image/check.png\" width=\"35px;\"></div><div class=\"todo list-item\">"+data[i].todo+"</div><div class=\"date list-item\">"+date+"<div class=\"delete\"><a class=\"delete-item\" href=\"#\">X</a><span class=\"row-date\">"+data[i].date+"</span></div></div></li>";
	         		$(".items").append(item);
					connectEvent(data[i].id);
			
					$("#"+data[i].id+"").find(".todo").css({
							"text-decoration":"line-through",
							"color" : "#d9d9d9"
					});
				}
			}
		});

	});
	// clear complete 눌렀을때 이벤트
	$(".clear-completed").click(function(event){
		$.ajax({
			 url:'/api/todos',
	         type:'DELETE',
	         contentType: "application/json; charset=utf-8",
	         dataType:'json',
	         success :function(data){
	         	removeItemAll();
	         	refresh();
			}
		});
	});


})(window);
