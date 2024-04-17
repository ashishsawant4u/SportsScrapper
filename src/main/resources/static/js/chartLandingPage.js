$( document ).ready(function() {
	console.log( "chart page" );
	
	getChartOddsData();
	
	
	
});

function toogleDataSeries(e){
	if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
		e.dataSeries.visible = false;
	} else{
		e.dataSeries.visible = true;
	}
	chart.render();
}


function getChartOddsData()
{
	
	var team1Back = [];
	var team1Lay = [];
	var team2Back = [];
	var team2Lay = [];
	var oddsData = {};
	
	//ajax call for GET method
	$.ajax({
		type: "GET",
        url: "http://localhost:8047/chart/33181109",
        dataType: "json",
        success: function(data) {
            console.log(data.length);
            oddsData["team1Name"] = data[0].team1Name;
            oddsData["team2Name"] = data[0].team2Name;
            
            
            //iterate over json array
            for (var i = 0; i < data.length; i++) 
            {
                var obj = {
                    x: new Date(data[i].createDateTime),
                    y: parseFloat(data[i].team1Back)
                };
                team1Back.push(obj);
            }
            
            //similary do it for team1lay
            for (var i = 0; i < data.length; i++) 
            {
                var obj = {
                    x: new Date(data[i].createDateTime),
                    y: parseFloat(data[i].team1Lay)
                };
                team1Lay.push(obj);
            }
           //similary do it for team2back and team2lay
            for (var i = 0; i < data.length; i++) 
            {
                var obj = {
                    x: new Date(data[i].createDateTime),
                    y: parseFloat(data[i].team2Back)
                };
                team2Back.push(obj);
            }
            
            for (var i = 0; i < data.length; i++) 
            {
                var obj = {
                    x: new Date(data[i].createDateTime),
                    y: parseFloat(data[i].team2Lay)
                };
                team2Lay.push(obj);
            }
            
            oddsData["team1Back"] = team1Back;
            oddsData["team1Lay"] = team1Lay;
            oddsData["team2Back"] = team2Back;
            oddsData["team2Lay"] = team2Lay;
           
           prepareOddsChart(oddsData);
        },
        error: function(data) {
            console.log(data);
        }
	})
}

function prepareOddsChart(oddsData) 
{
	var chart = new CanvasJS.Chart("chartContainer", {
	theme: "light1", // "light1", "light2", "dark1", "dark2"	
	animationEnabled: true,	
	zoomEnabled: true,	
	title: {
		text: "Odds Graph"
	},
	axisX: {
		valueFormatString: "hh:mm:ss",
		crosshair: {
			enabled: true,
			snapToDataPoint: true
		}
	},
	axisY2: {
		title: "Rate",
		valueFormatString: "#.0",
		crosshair: {
			enabled: true,
			snapToDataPoint: true
		}
	},
	toolTip: {
		shared: true
	},
	legend: {
		cursor: "pointer",
		verticalAlign: "top",
		horizontalAlign: "center",
		dockInsidePlotArea: true,
		itemclick: toogleDataSeries
	},
	data: [{
		type:"line",
		axisYType: "secondary",
		name: oddsData.team1Name+" Back",
		showInLegend: true,
		markerSize: 0,
		dataPoints:  oddsData.team1Back
	},
	{
		type: "line",
		axisYType: "secondary",
		name: oddsData.team1Name+" Lay",
		showInLegend: true,
		markerSize: 0,
		dataPoints: oddsData.team1Lay
	},
	{
		type: "line",
		axisYType: "secondary",
		name: oddsData.team2Name+" Back",
		showInLegend: true,
		markerSize: 0,
		dataPoints: oddsData.team2Back
	},
	{
		type: "line",
		axisYType: "secondary",
		name: oddsData.team2Name+" Lay",
		showInLegend: true,
		markerSize: 0,
		dataPoints: oddsData.team2Lay
	}]
});
chart.render();
}