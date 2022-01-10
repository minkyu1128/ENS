
/**
 * tui Grid 도구
 * 	-참조사이트
 * 	 ->듀토리얼: https://github.com/nhn/tui.grid/tree/master/packages/toast-ui.grid/docs/ko
 *   ->예제: http://nhn.github.io/tui.grid/latest/tutorial-example01-basic 
 *  @author 박민규
 *  @date 2020.05.25.
 */
var XitTuiGridConfig = function(){
	var _instance;
	
	var _gridId = 'grid';               //[필수]Grid를 출력할 Element Id
	var _data = {};                     //[선택]DataSource 정보(readData|createData|updateData|modifyData|deleteData 등)
	var _header = {};                   //[선택]헤더정보(헤더 명칭 및 매핑 field)
	var _columns = [];                  //[필수]컬럼정보(헤더 명칭 및 매핑 field)
	var _rowHeaders;                    //[선택]ROW 헤더 타입(rowNum: 순번, checkbox: 체크박스)
	var _bodyHeight;                    //[선택]Grid 높이 (number(단위: px)|'auto'|'fitToParent')
	var _minBodyHeight;                 //[선택]Grid 최소 높이 (단위: px)
	var _rowHeight = 25;                //[선택]Grid row 높이 (number(단위: px)|'auto' )     
	var _minRowHeight = 25;             //[선택]Grid row 최소 높이 (단위: px)     
	var _pageOptions = { perPage: 10 }; //[선택]한 페이지에 출력할 건수      
	var _columnOptions = {              //[선택]고정 컬럼
						frozenCount: 1 //고정컬럼 갯수
						, frozenBorderWidth: 2 //고정컬럼 보더(border) 두께
						, minWidth: 100 //최소 사이즈
					}
	var _summary;                       //[선택]하단합계
	var _treeColumnOptions = {};        //[선택]tree 구조 grid
	
	
	
	return {
		/** gridId 값을 설정 한다. */
		setOptGridId : function(val){
			this._gridId = val;
		},
		/** gridId 값을 반환 한다. */
		getOptGridId : function(){
			return this._gridId;
		},
		/** data 값을 설정 한다. */
		setOptDataSource : function(val){
			this._data = val;
		},
		/** data 값을 반환 한다. */
		getOptDataSource : function(){
			return this._data;
		},
		/** gridHeight 값을 설정 한다. */
		setOptGridHeight : function(val){
			this._bodyHeight = val;
		},
		/** gridHeight 값을 반환 한다. */
		getOptGridHeight : function(){
			return this._bodyHeight;
		},
		/** minGridHeight 값을 설정 한다. */
		setOptMinGridHeight : function(val){
			this._minBodyHeight = val;
		},
		/** minGridHeight 값을 반환 한다. */
		getOptMinGridHeight : function(){
			return this._minBodyHeight;
		},
		/** rowHeight 값을 설정 한다. */
		setOptRowHeight : function(val){
			this._rowHeight = val;
		},
		/** rowHeight 값을 반환 한다. */
		getOptRowHeight : function(){
			return this._rowHeight;
		},
		/** minRowHeight 값을 설정 한다. */
		setOptMinRowHeight : function(val){
			this._minRowHeight = val;
		},
		/** minRowHeight 값을 반환 한다. */
		getOptMinRowHeight : function(){
			return this._minRowHeight;
		},
		/** header 값을 설정 한다. */
		setOptHeader : function(val){
			this._header = val;
		},
		/** header 값을 반환 한다. */
		getOptHeader : function(){
			return this._header;
		},
		/** columns 값을 설정 한다. */
		setOptColumns : function(val){
			this._columns = val;
		},
		/** columns 값을 반환 한다. */
		getOptColumns : function(){
			return this._columns;
		},
		/** rowHeaderType 값을 설정 한다. */
		setOptRowHeaderType : function(val){
			this._rowHeaders = val;
		},
		/** rowHeaderType 값을 반환 한다. */
		getOptRowHeaderType : function(){
			return this._rowHeaders;
		},
		/** pageOptions 값을 설정 한다. */
		setOptPageOptions : function(val){
			this._pageOptions = val;
		},
		/** pageOptions 값을 반환 한다. */
		getOptPageOptions : function(){
			return this._pageOptions;
		},
		/** columnOptions 값을 설정 한다. */
		setOptColumnOptions : function(val){
			this._columnOptions = val;
		},
		/** columnOptions 값을 반환 한다. */
		getOptColumnOptions : function(){
			if(this._columnOptions==null)
				this._columnOptions = {};
			//컬럼 사이즈 조정 Default 설정
			if(this._columnOptions['resizable']==undefined||this._columnOptions['resizable']==null){	//컬럼 사이즈 조정(resizeable) 설정 값이 없으면..
				this._columnOptions['resizable'] = true;	//조정 가능(true) 으로 설정
			}
			return this._columnOptions;
		},
		/** summary 값을 설정 한다. */
		setOptSummary: function(val){
			this._summary = val;
		},
		/** summary 값을 반환 한다. */
		getOptSummary: function(){
			return this._summary;
		},
		/** treeColumnOptions 값을 설정 한다. */
		setOptTreeColumnOptions: function(val){
			this._treeColumnOptions = val;
		},
		/** treeColumnOptions 값을 반환 한다. */
		getOptTreeColumnOptions: function(){
			return this._treeColumnOptions;
		},
		/** instance 객체를 설정 한다. */
		setInstance : function(val){
			this._instance = val;
		},
		/** instance 객체를 반환 한다. */
		getInstance : function(){
			return this._instance;
		},
		/** tui Grid Instance */
		instance : function(tuiGrid){
			//Option Set
			var option = new Object();
			option.scrollX = true;
			option.scrollY = true;
			option.el = fnIsEmpty(this.getOptGridId())?document.getElementById(_gridId):document.getElementById(this.getOptGridId());
			if(!fnIsEmpty(this.getOptDataSource()))    option.data = this.getOptDataSource();
			if(!fnIsEmpty(this.getOptHeader()))        option.header = this.getOptHeader();
			if(!fnIsEmpty(this.getOptColumns()))       option.columns = this.getOptColumns();
			if(!fnIsEmpty(this.getOptRowHeaderType())) option.rowHeaders = [this.getOptRowHeaderType()];
			if(!fnIsEmpty(this.getOptGridHeight()))    option.bodyHeight = Number(this.getOptGridHeight()); 
			if(!fnIsEmpty(this.getOptMinGridHeight())) option.minBodyHeight = Number(this.getOptMinGridHeight()); 
			option.rowHeight    = fnIsEmpty(this.getOptRowHeight())   ?_rowHeight:Number(this.getOptRowHeight()); 
			option.minRowHeight = fnIsEmpty(this.getOptMinRowHeight())?_minRowHeight:Number(this.getOptMinRowHeight()); 
			if(!fnIsEmpty(this.getOptPageOptions()))   option.pageOptions = this.getOptPageOptions();
			if(!fnIsEmpty(this.getOptColumnOptions())) option.columnOptions = this.getOptColumnOptions();
			if(!fnIsEmpty(this.getOptSummary()))       option.summary = this.getOptSummary();
			if(!fnIsEmpty(this.getOptTreeColumnOptions())) option.treeColumnOptions = this.getOptTreeColumnOptions();


			//2020.11.06 박민규 - 요청에 대한 Response 및 Exception 처리를 위한 "AJAX" 헤더 추가
			if(!fnIsEmpty(option.data)){
				var headers = { 'AJAX': true };
				option.data['headers'] = headers;
			}
			
			//Grid Instance
			var instance = new tuiGrid(option);
			
			//instance Grid Set
			this.setInstance(instance);
			
			//2020.11.06 박민규 - Api 호출결과 메시지 출력 이벤트 리스너 추가.
			//Api Call Result EventListener Set
			// 결과가 false인 경우 발생한 경우
			instance.on('successResponse', function(ev){
				var msg = JSON.parse(ev.xhr.response).message;	//tui-grid 기본 format 메시지
				console.log(msg);
			});
			// 결과가 false인 경우 발생한 경우
			instance.on('failResponse', function(ev){
				try {
					var msg = JSON.parse(ev.xhr.response).message;	//tui-grid 기본 format 메시지
					if(fnIsEmpty(msg))
						msg = JSON.parse(ev.xhr.response).resp.resultMsg;
					if(!fnIsEmpty(msg))
						alert(msg);
				} catch (e) {
				}
			});
			// 오류가 발생한 경우
			instance.on('errorResponse', function(ev){
				try {
					var msg = JSON.parse(ev.xhr.response).message;	//tui-grid 기본 format 메시지
					if(fnIsEmpty(msg))
						msg = JSON.parse(ev.xhr.response).resp.resultMsg;
					if(!fnIsEmpty(msg))
						alert(msg);
				} catch (e) {
				}
			});

			
			
			/* 공백(empty) 여부 확인 */
			function fnIsEmpty(val){
				if(val==undefined||val==null||val==''||val==[]||val=={})
					return true;
				else
					return false;
			}
			
			return instance;
		},
		/** column 순서 변경 */
		fnColumnMove : function(curIdx, moveIdx){
			/*
			* 유효성 확인
			*/
			if(curIdx == moveIdx)
				return false;
			
			/*
			* 순서 변경
			*/
			var _columns = this.getOptColumns();
			var columnsRecordered = [];
			var isMoveRight = Number(curIdx) < Number(moveIdx);
			var isMoveLeft  = Number(curIdx) > Number(moveIdx);
			for(var i=0; i<_columns.length; i++){
				if(isMoveLeft){
					if(i == moveIdx)
						columnsRecordered.push(_columns[curIdx]);
				}
				
				if(i != curIdx)
					columnsRecordered.push(_columns[i]);
				
				if(isMoveRight){
					if(i == moveIdx)
						columnsRecordered.push(_columns[curIdx]);
				}
			}
			
			/*
			* 결과 반영
			*/
			this.getInstance().setColumns(columnsRecordered);
			this.setOptColumns(columnsRecordered);
		},
		/** Excel Export */
		exportExcel : function(fileName){
			/* 필수값 설정 */
			var _gridId = this.getOptGridId();
			var _instance = this.getInstance();
			var _frstColTyp = this.getOptRowHeaderType();
			var _arrHeader = [];
			var _arrName = [];
			var _mCustomRenderer = {};
			/* =================
			 * 2021.04.30. 박민규
			 *  컬럼 취득 방법 변경
			 *   AsIs: GridConfig 에 설정한 Columns 정보
			 *   ToBe: instance 에 설정된 Columns 정보 
			 ================= */
			//2021.04.30. 주석처리
//			this.getOptColumns().forEach(function(opt, idx){
			_instance.getColumns().forEach(function(opt, idx){
				_arrHeader.push(opt.header);
				_arrName.push(opt.name);
				//2021.04.30. 주석처리
//				if(!fnIsEmpty(opt.renderer)){
				if(!(fnIsEmpty(opt.renderer)||'DefaultRenderer'==opt.renderer.type.name||fnIsEmpty(opt.renderer.type.name))){	//브라우저별 opt.renderer.type.name 값의 차이 => Chrome: "DefaultRenderer", IE: undefined
					_mCustomRenderer[opt.name] = opt.renderer; 
				}
			});
			
			
			
			/* grid head Setting */
			var elementTHEAD = document.createElement('table');
			var $sltHeader = $('#'+_gridId+' .tui-grid-header-area > table.tui-grid-table').clone();
			//head 처리(table 고정셀 이용 시)
			if($sltHeader.length>1){
				//좌측 table의 head Selector
				var firstTableTh = $sltHeader.find('tr:last-child th');
				//우측 table row의 헤드(th)를 좌측 table row에 병합
				for(var i=1; i<$sltHeader.length; i++){
					$sltHeader.eq(i).find('tr').each(function(idx, row){
						var firstTableRow = $sltHeader.eq(0).find('tr:eq('+idx+')');
						if(firstTableRow.length == 0){
							//<tr> 신규생성
							var tr = document.createElement('tr');
							tr.innerHTML = row.innerHTML;
							$sltHeader.eq(0).append(tr);
							//firstTable <th>의 rowspan 변경
							for(var j=0; j<firstTableTh.length; j++){
								firstTableTh.eq(j).attr('rowspan', Number(firstTableTh.eq(j).attr('rowspan'))+1);
							}
						}
						else{
							firstTableRow.append(row.innerHTML);
						}
					});
				}
			}
			elementTHEAD.innerHTML = $sltHeader.eq(0).find('tbody').html();
			
			
			
			/* grid body Setting */
			var elementTBODY = document.createElement('tbody');
			_instance.getData().forEach(function(row, idxRow){
				var elementTR = document.createElement('tr');
				
				//타입별 <td> element 생성( 첫번째 컬럼타입 설정 시 )
				if(!fnIsEmpty(_frstColTyp))
					elementTR.appendChild(fnCreateTdByFrstCol(_frstColTyp, row));
				//<td> element 생성
				_arrName.forEach(function(columnName, idxColumn){
					var columnVal = '';
					var customRenderer = _mCustomRenderer[columnName];
					if(fnIsEmpty(customRenderer)){
						//column Value 설정
						columnVal = fnNvl(row[columnName]);
					}else{
						//prop 객체 생성
						var columnInfo = {};
						columnInfo['renderer'] = customRenderer;
						columnInfo['name'] = columnName;
						var props = {};
						props['columnInfo'] = columnInfo;
						props['rowKey'] = row.rowKey;
						props['grid'] = _instance;
						
						//렌더러 호출
						customRenderer.type(props);
						
						//column Value 설정
						columnVal = fnNvl(row[columnName]);
						if(row.rowKey<15){
//							console.log('[export]rowKey->'+row.rowKey+'/ column->'+columnName+' / value->'+row[columnName]);
//							console.log(_instance.getRow(row.rowKey));
						}
					}
					
					var elementTD = document.createElement('td');
					elementTD.innerHTML = columnVal;
					elementTR.appendChild(elementTD);
				});
				elementTBODY.appendChild(elementTR);
			});
			
			
			
			/* grid table Setting */
			var elementTABLE = document.createElement('table');
			elementTABLE.appendChild(elementTHEAD);
			elementTABLE.appendChild(elementTBODY);
			
			
			
			/* export Excel */
			var fileUtil = new XitFileExportUtil(fileName, 'table', elementTABLE);
			fileUtil.exportExcel();
			
			
			
			
			
			

			/* member Function Declare */
			//isEmpty Function
			function fnIsEmpty(val){
				if(val==undefined||val==null||val==''||val=={}||val==[])
					return true;
				return false;
			}
			//nvl Function
			function fnNvl(val, replaceVal){
				replaceVal = fnIsEmpty(replaceVal)?'':replaceVal;
				val = fnIsEmpty(val)?replaceVal:val;
				return val;
			}
			//<td> element 생성 Function
			function fnCreateTdByFrstCol(colType, row){
				var returnVal = '';
				
				/* column 타입별 value 설정 */
				switch (colType) {
					case 'rowNum':
						returnVal = row._attributes.rowNum;
						break;
					case 'checkbox':
						if(row._attributes.checked)
							returnVal = '☑';
						else
							returnVal = '□';
						break;
					default:
						return returnVal;
						break;
				}
				
				/* <td> element 생성 */
				var elementFrstTD = document.createElement('td');
				elementFrstTD.innerHTML = returnVal;
				
				
				return elementFrstTD;
			}
		}
		
		
	}
}








/**
 * Button 렌더러
 *  -설명: Grid의 cell에 Button을 생성 한다.
 *  	버튼명칭에 format 사용이 가능하며 사용방법은 아래와 같다.
 *  	ex) value: 'A is {0}. B is {1}. {0}!={1}',
 *  		listColumns: ['컬럼1', '컬럼2']
 *  @param value 버튼에 출력 할 명칭
 *  @param listColumns format에 매칭할 컬럼 목록
 *  @param callbackFnc 버튼 클릭 시 호출 할 함수명
 *  @author 박민규
 *  @date 2020.05.28.
 */
var XitButtonRenderer = function(props){
//	console.log('XitButtonRenderer Called!!-> '+props.columnInfo.name);
	//options get
	var opt = props.columnInfo.renderer.options;
	var value = opt.value;
	var callbackFnc = opt.callbackFnc;
	var args = opt.listColumns;
	
	//"value" Formatting 
	if(args != undefined){
		var row = props.grid.getRow(props.rowKey);
		for(var i=0; i<args.length; i++){
			var regEx = new RegExp('\\{'+i+'\\}', 'gi');
			value = value.replace(regEx, row[args[i]]);
		}
	}
	
	//Element Setting
	var el = document.createElement('input');
	el.type = 'button';
	el.value = value;
	
	//EventListener Setting
	el.addEventListener('click', function(){
		var callback = callbackFnc+'(props)';
		eval(callback);
	});
	
	//Element render
	this.el = el;
//	this.render(props);
	if(props.rowKey<15){
//		console.log('[Renderer]rowKey->'+props.rowKey+' column->'+props.columnInfo.name+' / el.value->'+el.value);
	}
	//DataSet Injection ( Dataset에 추가해야 "복사(ctrl+c)" 기능 사용 가능 )
	props.grid.setValue(props.rowKey, props.columnInfo.name, el.value, false);
}
XitButtonRenderer.prototype.getElement = function(){
	return this.el;
}
XitButtonRenderer.prototype.render = function(props){
	this.el.value = props.value;
}




/**
 * column 병합 렌더러
 *  -설명: 다수의 Column을 하나의 Column으로 병합하여 cell에 출력 한다.
 *  	필요 시 format 사용이 가능하며 사용방법은 아래와 같다. (※포맷 사용 시 "구분자(separator)"는 적용되지 않는다)
 *  	[단순 컬럼 병합]
 *  	ex) listColumns: ['컬럼1', '컬럼2'],
 *  		separator: '/'
 *  	[포맷사용 컬럼 병합]
 *  	ex) listColumns: ['컬럼1', '컬럼2'],
 *  		format: 'A is {0}. B is {1}. {0}!={1}'
 *  @param listColumns	병합할 컬럼 name 목록	
 *  @param separator	컬럼 연결 구분자(default: 공백(' '))	
 *  @param format       출력 포맷
 *  @author 박민규
 *  @date 2020.05.28.
 */
var XitColumnMergeRenderer = function(props){
//	console.log('XitColumnMergeRenderer Called!!-> '+props.columnInfo.name);
	//options get
	var opt = props.columnInfo.renderer.options;
	var args = opt.listColumns;
	var separator = opt.separator;
	if(fnIsEmpty(separator))
		separator = ' ';
	var value = opt.format;
	
	//Columns merge
	if(fnIsEmpty(value)){
		var arrStr = [];
		var row = props.grid.getRow(props.rowKey);
		args.forEach(function(column){
			arrStr.push(row[column]);
		});
		value = arrStr.join(separator);
	}else{
		var row = props.grid.getRow(props.rowKey);
		for(var i=0; i<args.length; i++){
			var regEx = new RegExp('\\{'+i+'\\}', 'gi');
			value = value.replace(regEx, fnNvl(row[args[i]]));
		}
	}
	function fnNvl(val){
		if(val==undefined||val==null)
			return '';
		return val;
	}
	function fnIsEmpty(val){
		if(val==undefined||val==null||val==''||val==[]||val=={})
			return true;
		return false;
	}
	
	//Element Setting
	var el = document.createElement('div');
	el.setAttribute('class', 'tui-grid-cell-content');
	el.innerHTML = value;
	
	//Element render
	this.el = el;
//	this.render(props);
	if(props.rowKey<15){
//		console.log('[Renderer]rowKey->'+props.rowKey+' column->'+props.columnInfo.name+' / el.value->'+el.innerHTML);
	}
	
	//DataSet Injection ( Dataset에 추가해야 "복사(ctrl+c)" 기능 사용 가능 )
	props.grid.setValue(props.rowKey, props.columnInfo.name, el.innerHTML, false);
}
XitColumnMergeRenderer.prototype.getElement = function(){
	return this.el;
}
XitColumnMergeRenderer.prototype.render = function(props){
	this.el.value = props.value;
}


/**
 * Button 렌더러
 *  @author 최유수
 *  @date 2020.07.15.
 *  formatter 		: 셀 안에 입력될 문자열.
	,eventType		: 셀에 걸 이벤트.
	,eventFunction 	: 이벤트에 부여될 함수.
	
	element 속성
	element : 생성 엘리먼트 속성
	type : 생성 엘리먼트의 타입
	value : 들어갈 텍스트
 */
/**
 * 요구사항
 * 셀 속성을 부여할때 
 * 
 * 1.셀에 개발자가 원하는 Element를 생성 할 수 있음
 * 2.1의 속성에 개발자가 원하는 이벤트를 부여 할 수 있음
 * 3. 2의 이벤트에 개발자가 원하는 함수를 부여 할 수 있음
 * 4. 개발자가 원하는만큼 유형을 추가 할 수 있음.(유형이 여러개일수도 아닐수도 있음)
 */
var CustomButtonRenderer = function(props){
	//options get
	var opt = props.columnInfo.renderer.options;
	//formatter를 구성할 인자값(해당 인자값은 함수형일때 배열일 수 있음.)
	var formatter = opt.formatter;
	//이벤트로 사용할 함수
	var eventFunction = opt.eventFunction;
	//이벤트 속성
	var eventType = opt.eventType;
	//객체 유형
	var element = opt.element;
	//객체 속성
	var type = opt.type;
	
	
	var formattObject = formatter;
	if(typeof formatter =="function"){
		//formatter가 함수에 의해 구현이됨, Return값은 Arr<Object>, Objcect임;
		//Object는 formatter : Text 와 Element로 구성됨.
		formattObject = formatter(props);
	}else{
		//formatter가 단순 텍스트임.
		formattObject = opt;
	}
	
	var ele = cellMaker(formattObject ,  eventFunction , eventType , element ,  type , props);
	this.el = ele;
}
CustomButtonRenderer.prototype.getElement = function(){
	return this.el;
}

//셀을 만듦.
var cellMaker = function(formattObject , eventFunction , eventType , element ,  type , props){
	var spanEle = document.createElement("span");
	var ele;
	
	//셀에 1개 이상의 데이터가 들어가는 경우
	if(formattObject instanceof Array && formattObject.length != 0){
		formattObject.forEach(function(elementObject , index , arrays){
			elementObject["eventFunction"] = eventFunction;
			elementObject["eventType"] = eventType;
			ele = elMaker(elementObject , props);
			$(spanEle).append(ele);
			//객체가 다수이면 띄어쓰기 함
			if(index < formattObject.length -1){
				$(spanEle).append(document.createTextNode('\u00a0  // \u00a0'));
			}
		});
	}
	
	//셀에 데이터가 1개이지만 함수로 Obj를 가져와서 만듦
	if(formattObject instanceof Object){
		formattObject["eventFunction"] = eventFunction;
		formattObject["eventType"] = eventType;
		ele = elMaker(formattObject , props);
		spanEle.appendChild(ele);
	}
	
	return spanEle;
}

var elMaker = function(elementObject,  props){
	var text = 				elementObject.formatter
	var eventType = 		elementObject.eventType
	var element = 			elementObject.element
	var eventFunction = 		elementObject.eventFunction
	var type =				elementObject.type
	
	var ele;
	if(element == "text"){
		ele = document.createElement("p");
		ele.style.whiteSpace = 'nowrap';
		ele.style.display = 'inline-block';
		ele.style.fontSize = '11px';
		ele.style.fontFamily = 'Nanum Barun Gothic';
		ele.appendChild(document.createTextNode(text));
	}else{
		ele = document.createElement(element);
		ele.type = type;
		ele.value = text;
	}
	//이벤트가 함수이면 이벤트 부여함.
	if(typeof eventFunction =="function"){
		ele.addEventListener(eventType, function(event){eventFunction(props, event)});
	}
	
	return ele;
}

var RowNumberRenderer = function(props){
	var el = document.createElement('span');
	el.innerHTML = props.rowKey + 1
	this.el = el;
	
	getElement = function(){
		return this.el;
	      }

	render = function(props){
		this.el.innerHTML = props.rowKey + 1
	      }
  }

RowNumberRenderer.prototype.getElement = function(){
	return this.el;
}




