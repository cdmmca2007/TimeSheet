(function(self){
    var tip = null;
    function clone(obj){
        return JSON.parse(JSON.stringify(obj))
    }
    function initCombo(name,q,op){
        $.ajaxService({
            url:'service/generic/'+q,
            success: function(data){
                var d =$.extend({
                    data:data
                },op);
                $("#modal-win input[name="+name+"]").select2(d);
            }
        });
    };
    function initTip(){
        /* tooltip*/
        var tip  = d3.select("body").append("div")   
        .attr("class", "tooltip")               
        .style("opacity", 0);
        tip.append("div");
        return tip;
    }
    function showTip(tip,text){
            tip.select("div").html(text);
            tip.transition()        
            .duration(200)      
            .style("opacity", .9)   
            .style("left", (d3.event.pageX+10) + "px")     
            .style("top", (d3.event.pageY - 48) + "px");  
     }
    function hideTip(tip){
        tip.transition()        
        .duration(500)      
        .style("opacity", 0); 
    }
    function _make_x_axis(x,orient,ticks) {  
  
        var ax = d3.svg.axis()
        .scale(x)
        .orient("bottom")
        .tickSize(0)
        //.tickFormat(cfg.textFormater? cfg.textFormater: function(d){return  d;});
        if(ticks)
            ax.ticks(ticks);
        return ax;
    };
    function _make_y_axis(y,orient,ticks) { 
       
        var ax =d3.svg.axis()
        .scale(y)
        .orient(orient?orient:"left")
        .tickSize(0);
        if(ticks)
            ax.ticks(ticks);
        return ax;
    }
    function getLocations(dataSrc){
        var arr=[];
        for(var i=0;i<dataSrc.length;i++){
            if(arr.indexOf(dataSrc[i].locationname)===-1){
                arr.push(dataSrc[i].locationname)
            }
        }
        return arr;
    }
    function getActivities(dataSrc){
        var arr=[];
        for(var i=0;i<dataSrc.length;i++){
            if(arr.indexOf(dataSrc[i].activityname)===-1){
                arr.push(dataSrc[i].activityname)
            }
        }
        return arr;
    }
    function getSummaryData(dataSrc, loc){
         var groups ={},grpArray=[],sum=0;
         var index=0;
         
         for(var i=0; i<dataSrc.length; i++){
             var record = dataSrc[i];
             var key=dataSrc[i]["activityid"];
             
             if(groups[key]===undefined){ // new group
                 groups[key] = index;
                 var obj ={
                     tot_hour:record.tot_hour,
                     activity_tot_hour:record.activity_tot_hour,
                     activityid:record.activityid,
                     activityname:record.activityname,
                     locationid:record.locationid,
                     projectid:record.projectid,
                     projectname:record.projectname
                 };
                 for(var k=0;k<loc.length;k++){
                     obj[loc[k]]=0;
                 }
                 obj[record.locationname]=record.tot_hour;
                 grpArray.push(obj);
                 index++;
             }else{
                 var obj = grpArray[groups[key]];
                 obj[record.locationname]=record.tot_hour;
                 obj.tot_hour+=record.tot_hour;
             }
         }
 
           return grpArray;
    }
    function getLocationData(dataSrc, loc,activities){
         var groups ={},grpArray=[],sum=0;
         var index=0,
         sum=0,
         sumMap={};
         
         for(var i=0; i<dataSrc.length; i++){
             var record = dataSrc[i];
             var key=dataSrc[i]["locationid"];
             
             if(groups[key]===undefined){ // new group
                 groups[key] = index;
                 var obj ={
                     tot_hour:record.tot_hour,
                     locationid:record.locationid,
                     locationname:record.locationname
                 };
                 for(var k=0;k<loc.length;k++){
                     obj[loc[k]]=0;
                 }
                 obj[record.activityname]=record.tot_hour;
                 sum+=record.tot_hour;
                 grpArray.push(obj);
                 index++;
             }else{
                 var obj = grpArray[groups[key]];
                 obj[record.activityname]=record.tot_hour;
                 obj.tot_hour+=record.tot_hour;
                 sum+=record.tot_hour;
             }
             sumMap[record.activityname] = record.activity_tot_hour;
         }
         var Pending = {locationname:"Pending",tot_hour:0};
         var totalSum = 0;
         activities.forEach(function(d){
             Pending[d.activityname] = d.activity_tot_hour-d.tot_hour;
             Pending.tot_hour+=Pending[d.activityname];
             totalSum+=d.activity_tot_hour;
         });
         grpArray.push(Pending);
         //Calculate percentage
         var array = grpArray.map(function(d){
             d.percentage = d.tot_hour*.01;
             return d;
         });
         
         return array;
    }
    function updateDetail(tabid,selectedProject){
        $tab = $(tabid)
         var dat=new Date(selectedProject.startDate);
        var edat=new Date(selectedProject.endDate);
        $tab.find(".s_projectname").text(selectedProject.projectName+"("+selectedProject.projectCode+")");
        $tab.find(".proj-status").addClass(selectedProject.projStatusname).attr('title',selectedProject.projStatusname);
        $tab.find(".s_projectmanager").text(selectedProject.projectManagername);
        $tab.find(".s_startdate").text("[ "+dat.getDate()+"/"+dat.getMonth()+"/"+dat.getFullYear()+"-"+edat.getDate()+"/"+edat.getMonth()+"/"+edat.getFullYear()+" ]");
        $tab.find(".s_status").text();
        $tab.find(".s_client").text(selectedProject.clientName);
        $tab.find(".s_priority").text("[ "+selectedProject.priorityName+" ]");
        $tab.find(".s_projecttype").text("[ "+selectedProject.projectTypeName+" ]");
       
    }
    function createGrid(selectedProject,dataTable,data,loc){
        var col = [{
                "data": 'activityname',
                title:"Activity Name",
                width:'15%'
            },{
                "data": 'activity_tot_hour',
                title:"Hour Assigned"
            }];
        for(var j=0; j<loc.length;j++){
            col.push({
                title:loc[j],
                data:loc[j]
            });
        };
        col.push({
                "data": 'tot_hour',
                title:"Total Hour",
                width:'15%'
            },{
                "data": 'percent',
                title:"% Done",
                width:'15%',
                render:function(val,type,row){
                    var num=((row.tot_hour/row.activity_tot_hour)*100);
                    num=num.toFixed(2);
                    return '<font color=green><b>'+num+'%</b></font>';
            }
            }
            ); 
        dataTable.dataTable({
            "bLengthChange": false,
            data:data,
            "columns":col
        });

        var $grid = dataTable.closest(".dataTables_wrapper");
        //var toolbar = "<div style='float:left;right-padding:10px'></div>";
        //$("#project-activity-report-grid_filter").prepend(toolbar); 
        dataTable.find('tbody').on( 'click', 'tr', function () {
            if ( $(this).hasClass('row-selected') ) {
                $(this).removeClass('row-selected');
            }
            else {
                dataTable.$('tr.row-selected').removeClass('row-selected');
                $(this).addClass('row-selected');
            }
        } );
 
//        $('#button').click( function () {
//            dataTable.row('.selected').remove().draw( false );
//        } );
//        $('#loc_chkbox').click(function(){
            
//        });
        
    }
     function createBulletChart(selector,dataSrc){
         
         var max = d3.max(dataSrc, function(d){
             return d.activity_tot_hour;
             
         });
         
         
         var data = dataSrc.map(function(d){
             return {
                 "title":d.activityname,
                 "subtitle":"in Hours",
                 "ranges":[d.activity_tot_hour,max],
                 "measures":[d.tot_hour],
                 "markers":[d.activity_tot_hour]
             };
             
         });            
        var $el = $(selector);
        var margin = {top: 5, right: 40, bottom: 20, left: 100},
                width = $el.width() - margin.left - margin.right,
                height = (($el.height()-30)/data.length) - margin.top - margin.bottom;
                height = height>50?50:height;
                
        var chart = d3.bullet()
                      .width(width)
                      .height(height)
                      

        var svg = d3.select(selector).selectAll("svg")
                    .data(data)
                    .enter().append("svg")
                    .attr("class", "bullet")
                    .attr("width", width + margin.left + margin.right)
                    .attr("height", height + margin.top + margin.bottom)
                    .append("g")
                    .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
                    .call(chart); 
       var title = svg.append("g")
      .style("text-anchor", "end")
      .attr("transform", "translate(-6," + height / 2 + ")");
      

  title.append("text")
      .attr("class", "title")
      .text(function(d) {
          var text = d.title;
          return text.length>14?text.substr(0,12)+"..":text;
      });

  title.append("text")
      .attr("class", "subtitle")
      .attr("dy", "1em")
      .text(function(d) {return d.subtitle;});

     }
     function getGroups(dataSrc,groupField,field){
         var groups ={},grpArray=[],sum=0;
         var index=0;
         
         for(var i=0; i<dataSrc.length; i++){
             var key=dataSrc[i][groupField];
             
             if(groups[key]===undefined){
                 groups[key] = index;
                 var obj ={
                     tot_hour:dataSrc[i].tot_hour,
                     activity_tot_hour:dataSrc[i].activity_tot_hour
                 };
                 obj[groupField]=dataSrc[i][groupField];
                 obj[field]=[dataSrc[i][field]];
                 grpArray.push(obj);
                 index++;
             }else{
                 var obj = grpArray[groups[key]];
                 obj.tot_hour += dataSrc[i].tot_hour;
                 obj[field].push(dataSrc[i][field]);
             }
             sum+=dataSrc[i].activity_tot_hour;
         }
         
         
         var used =0;
         grpArray.forEach(function(d){
             d.PERCENT = d.tot_hour*100/d.activity_tot_hour;
             used+=d.tot_hour;
         });
         var pending ={
             tot_hour:sum-used,
             activity_tot_hour:sum,
             PERCENT:(sum-used)*100/sum
         }
         pending[groupField]="Pending"
         grpArray.push(pending)
           return grpArray;
         
     }
    function createActivityChart(selector,dataSrc,loc){
         
        var self = this,
        groupField ="activityname",
        xField = groupField,
        yField = "tot_hour";
        var data = dataSrc.map(function(d){
            d.ages =[];
            loc.forEach(function(key){
                d.ages.push({
                    name:key,
                    value:d[key],
                    key:d[groupField]+": "+d[yField]
                });
            });
            return d; 
        });
        
        var $selector= $(selector);
        var w = $selector.width()-80, //width
        h = $selector.height()-120, //height
        margin={
            left:50,
            top:50,
            right:30,
            bottom:50
        };
        color = d3.scale.ordinal().range(["#48a012", "#7b6888", "#6b486b","#48a0ee", "#a05d56","#DCA572"].slice(data.lenth)); //builtin range of colors
        var x = d3.scale.ordinal().rangeRoundBands([0, w], .1);
        var x1 = d3.scale.ordinal();
        var y = d3.scale.linear().range([h, 0]);
      
        var xAxis = _make_x_axis(x,"bottom");
        var yAxis =_make_y_axis(y, "left",5).tickFormat(d3.format(".2s"));
        var svg =d3.select(selector).append("svg")
        .attr("width", w + margin.left + margin.right)
        .attr("height", h + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
                       			
        x.domain(data.map(function(d) {
            return d[xField];
        }));
        x1.domain(loc).rangeRoundBands([0, x.rangeBand()]);
        y.domain([0,d3.max(data, function(d) { 
            return d3.max(d.ages, function(d) { 
                return d.value; 
            }); 
        })]);
        svg.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(0," + h + ")")
        .call(xAxis)
        .selectAll("text")  
        .style("text-anchor", "end")
        .attr("dx", "-.8em")
        .attr("dy", ".1em")
        .attr("y", 5)
        .attr("transform", function(d) {
        		return "rotate(-35)";
        })
        .text(function(text){
            return text.length>14?text.substr(0,12)+"..":text;
        })
        .on("mouseover", function(d) {  
        	showTip(tip,"Activity: "+d);
        })                  
        .on("mouseout", function(d) {       
        	hideTip(tip);
        });

        svg.append("g")
        .attr("class", "y axis")
        .attr("transform", "translate(0,"+0+")")
        .call(yAxis);
        //
        //drow grids
        svg.append("g")         
        .attr("class", "graph-grid")
        .attr("transform", "translate("+(0)+"," + h + ")")
        .call(_make_x_axis(x)
            .tickSize(-h, 0, 0)
            .tickFormat("")
            );

        svg.append("g")         
        .attr("class", "graph-grid")
        .attr("transform", "translate(0,"+0+")")
        .call(_make_y_axis(y,null,5)
            .tickSize(-w, 0, 0)
            .tickFormat("")
            );

        var state = svg.selectAll(".state")
        .data(data)
        .enter().append("g")
        .attr("class", "g")
        .attr("transform", function(d) {
            return "translate(" + x(d[xField]) + ",0)";
        });
        state.selectAll("rect")
        .data(function(d) {
            return d.ages;
        })
        .enter().append("rect")
        .attr("width", x1.rangeBand())
        .attr("x", function(d) {
            return x1(d.name);
        })
        .attr("y", function(d) {
            return y(d.value);
        })
        .attr("height", function(d) {
            return h - y(d.value);
        })
        .style("fill", function(d) {
            return color(d.name);
        })
        .attr("class", function(d,i){
            return "x-rect x-rect-"+d.name+" x-rect-"+i; 
        })
    	.on("mouseover", function(d) {  
    		showTip(tip, d.name+": "+d.value+"<br/>"+d.key);
    	})                  
    	.on("mouseout", function(d) {       
    		hideTip(tip);
    	})
        var legWidth = (w-100)/3;
        var legend = svg.selectAll(".legend")
		.data(loc)
		.enter().append("g")
		.attr("class", function(d,i){
			return "legend legend_class_"+i+" x-legend-"+d;  
		})
		.on('click',function(d,i){
		})
		.attr("transform", function(d, i) {return "translate(" + (i-1) * legWidth + ",-40)";});

		legend.append("rect")
			.attr("x", legWidth)
			.attr("width", 11)
			.attr("height", 12)
			.style("fill",color);

		legend.append("text")
			.attr("x", legWidth+15)
			.attr("y", 6)
			.attr("dy", ".35em")
			//.style("text-anchor", "end")
			.text(function(d) {return d;});
    }
     function createLocationChart(selector,dataSrc,loc,activities){
         
        var self = this,
        groupField = "locationname";
        var data = dataSrc;//getGroups(dataSrc,groupField,"activityname");
        
        var $selector= $(selector);
        var w = $selector.width(), //width
        h = $selector.height(), //height
        margin=30,
        marginLeft=15,
        marginTop=15,
        r = (h-margin)/2, //radius
        
        color = d3.scale.ordinal().range(["#DCA572","#4D7CEA","#c04c4a","#9BCF7B","#48a012", "#7b6888", "#6b486b","#48a0ee", "#a05d56","#DCA572"].slice(data.lenth)); //builtin range of colors
        var selector = d3.select(selector)
        .append("svg:svg") //create the SVG element inside the <body>
        .data([data]) //associate our data with the document
        .attr("width", w) //set the width and height of our visualization (these will be attributes of the <svg> tag
        .attr("height", h)
        .append("svg:g") //make a group to hold our pie chart
        .attr("transform", "translate(" + (r+marginLeft) + "," + (r+marginTop) + ")"); //move the center of the pie chart from 0, 0 to radius, radius
			 
        var arc = d3.svg.arc() //this will create <path> elements for us using arc data
        .outerRadius(r - 10)
        .innerRadius(r - 70);
			 
        var pie = d3.layout.pie() //this will create arc data for us given a list of values
        .value(function(d) {
            return d.tot_hour;
        }); //we must tell it out to access the value of each element in our data array
			 
        var arcs = selector.selectAll("g.slice") //this selects all <g> elements with class slice (there aren't any yet)
        .data(pie) //associate the generated pie data (an array of arcs, each having startAngle, endAngle and value properties)
        .enter() //this will create <g> elements for every "extra" data element that should be associated with a selection. The result is creating a <g> for every object in the data array
        .append("svg:g") //create a group to hold each slice (we will have a <path> and a <text> element associated with each slice)
        .attr("class", "slice")
        .on("mouseover", function(d) { 
            var text = "Total Hours: "+d.data.tot_hour;
            for(var l=0;l<activities.length;l++){
                text+="<br>"+activities[l]+": "+d.data[activities[l]];
            }
            
            showTip(tip,text);
        })                  
        .on("mouseout", function(d) {       
            hideTip(tip);
        });
        ; //allow us to style things in the slices (like text)
			 
        arcs.append("svg:path")
        .attr("fill",function(d, i) {
            return color(i);
        } ) //set the color for each slice to be chosen from the color function defined above
        .attr("d", arc); //this creates the actual SVG path using the associated data (pie) with the arc drawing function
			 
        arcs.append("svg:text") //add a label to each slice
        .attr("transform", function(d) { //set the label's origin to the center of the arc
            //we have to make sure to set these before calling arc.centroid
            //d.innerRadius = 0;
            //d.outerRadius = r;
            return "translate(" + arc.centroid(d) + ")"; //this gives us a pair of coordinates like [50, 50]
        })
        .attr("text-anchor", "middle") //center the text on it's origin
        .text(function(d, i) {
            return data[i].percentage.toFixed(1)+"%";
        })
			 
	var legends = clone(loc);
        legends.push("Pending");
        var legend = selector.selectAll(".piechart-legend")
        .data(legends)
        .enter().append("g")
        .attr("class", "piechart-legend")
        .attr("transform", function(d, i) { 
            return "translate("+(margin+10)+","+(-20-(i*20))+")"; 	
        });

        legend.append("rect")
        .attr("x", 70)
        .attr("width", 11)
        .attr("height", 12)
        .style("fill",function(d, i) {
            return color(i);
        } );
	
        legend.append("text")
        .attr("x", 85)
        .attr("y", 10)
        //.attr("dy", ".35em")
        //.style("text-anchor", "end")
        .text(function(d) {
            return d;
        });
    }
    self.ProjectActivityReport = function(selectedProject){
        var tabId = "#ProjDetail_"+selectedProject.projectId;
        tip = initTip();
        var dataTable=$(tabId+" #project-activity-report-grid");
        var dataSource =null,
        actData=null;
        var loc =null;
        var act = null;
        $.ajax({
            url:"service/projactvtyrep/"+selectedProject.projectId,
            "type": "GET",
            success: function(res){
                loc =getLocations(res.data);
                dataSource = res.data;
                actData = getSummaryData(dataSource, loc);
                createGrid(selectedProject,dataTable,actData,loc);
                createBulletChart(tabId+" #activity-bullet-chart",actData);
                createActivityChart(tabId+" #activity-report-chart",actData, loc);
            }
        });
        updateDetail(tabId,selectedProject)
        $(tabId+" #activityGraphContainer .btn-group input").change(function(){
            var label = $(this).closest("label");
            if(!label.hasClass("btn-primary")){
                 var  parent = $(this).closest(".btn-group");
                 parent.find(".btn-primary").removeClass("btn-primary")
                 label.addClass("btn-primary");
                 var text = label.text();
                 $(tabId+" #activity-report-chart *").remove();
                 if(text.trim()==="Activity"){   
                     createActivityChart(tabId+" #activity-report-chart",actData, loc);
                 }else{
                     if(!act){
                        act = getActivities(dataSource);
                     }   
                     var data = getLocationData(dataSource, act,actData);
                     createLocationChart(tabId+" #activity-report-chart",data,loc,act);
                 }
            }
           
        })
    };	
})(this);
