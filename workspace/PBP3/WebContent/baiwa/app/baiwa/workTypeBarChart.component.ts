import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {CommonService} from './../service/Common.service';
import { Http, Headers, Response } from '@angular/http';
declare var jQuery: any;

@Component({
    templateUrl: 'app/baiwa/html/workTypeBarChart.component.html'
})
export class workTypeBarChart {

	public libPath: string;
    public json: any;
    public nameDepart: any;
    public mean1: any;
	public user: any;
    public academicYearList: any;
    public currentAcademicYear: any;

    constructor(private commonService: CommonService, private http: Http) {
        this.libPath = "/PBP3/baiwa/libs/";

    }
    ngOnInit() {

		this.GetUserSession();


    }
    ngAfterViewInit() {

    }

    public GetUserSession() {
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(response => this.GetuserSucess(response),
            error => this.GetPersonError(error), () => console.log("editdone !"));
    }


	public GetuserSucess(response: any) {
        this.user = response.json(JSON.stringify(response._body));
        this.academicYearList = this.user.academicYearList;
        this.currentAcademicYear = this.user.currentAcademicYear;

        this.DepartmentName(this.currentAcademicYear);
    }

	public GetPersonError(error: String) {
        console.log("GetPersonError.")

    }

	public DepartmentName(Year: any) {

		this.currentAcademicYear = Year;
        var url = "../person/MinMaxBean/" + Year;
        return this.http.get(url).subscribe(response => this.GetkendoSucess(response),
			error => this.GetDepartmentNameError(error), () => console.log("DepartmentName !"));
    }

    public GetkendoSucess(response: any) {
        this.json = response.json(JSON.stringify(response._body));
        this.nameDepart = this.json.departmentName;

		this.kendoChart1();
		this.kendoChart2();
		this.kendoChart3();
		this.kendoChart4();
		this.kendoChart5();

    }

    public GetDepartmentNameError(error: String) {
        console.log("GetDepartmentNameError.")

    }

	public kendoChart1() {
		var start = this.json.mean1;
		var end = start + 2;
		var startMin = this.json.minValue1;
		var endMin = startMin + 2;
		var startMax = this.json.maxValue1;
		var endMax = startMax + 2;
		var year = this.currentAcademicYear;

		jQuery("#chart1").kendoChart({
			dataSource: {
				transport: {
					read: {
						url: "../person/getWorkTypeBarchart/1/" + year,
						cache: false,
						dataType: "json"
					}
				},

			},
			title: {
				text: "ระดับคะแนนในภาควิชา ด้านวิชาการ ค่่าเฉลี่ย  " + start + "  "
				// text: "ระดับคะแนนในภาควิชา ด้านวิชาการ ค่่าเฉลี่ย  " + start + "  (เกณฑ์ขั้นต่ำ:" + this.json.minDesc1 + "   เกณฑ์ขั้นสูง:" + this.json.maxDesc1 + ")"
			},
			series: [{
				type: "column",
				field: "axisValue",
				name: "ระดับคะแนน"
			}],


			categoryAxis: {
				field: "axisName",
				labels: {
					rotation: -90
				}
			}
			// ,valueAxis: {
			//     min: 0,
			//     max: 1000,
			//     majorUnit: 100
			// } 
			, valueAxis: {
				plotBands: [
					{ from: start, to: end, color: "orange" },
					{ from: startMax, to: endMax, color: "green" },
					{ from: startMin, to: endMin, color: "red" }
				]
			}
			,
			tooltip: {
				visible: true,
				template: "#= series.name #: #= value #"
			}


		});
	}
	public kendoChart2() {

		var start = this.json.mean2;
		var end = start + 2;
		if (start == 0.00) {
			end = 0.00;
		}

		var startMin = this.json.minValue2;
		var endMin = startMin + 2;
		if (startMin == 0.00) {
			endMin = 0.00;
		}

		var startMax = this.json.maxValue2;
		var endMax = startMax + 2;
		var year = this.currentAcademicYear;

		jQuery("#chart2").kendoChart({
			dataSource: {
				transport: {
					read: {
						url: "../person/getWorkTypeBarchart/2/" + year,
						dataType: "json"
					}
				}
			},
			title: {
				text: "ระดับคะแนนในภาควิชา ด้านงานพัฒนาวิชาการ  ค่่าเฉลี่ย  " + this.json.mean2 + "  "
								// text: "ระดับคะแนนในภาควิชา ด้านงานพัฒนาวิชาการ  ค่่าเฉลี่ย  " + this.json.mean2 + "  (เกณฑ์ขั้นต่ำ:" + this.json.minDesc2 + "   เกณฑ์ขั้นสูง:" + this.json.maxDesc2 + ")"
			},
			series: [{
				type: "column",
				field: "axisValue",
				name: "ระดับคะแนน"
			}],
			categoryAxis: {
				field: "axisName",
				labels: {
					rotation: -90
				}
			},
			valueAxis: {
				plotBands: [
					{ from: start, to: end, color: "orange" },
					{ from: startMax, to: endMax, color: "green" },
					{ from: startMin, to: endMin, color: "red" }
				]
			}
			,
			tooltip: {
				visible: true,
				template: "#= series.name #: #= value #"
			}
		});

	}

	public kendoChart3() {

		var start = this.json.mean3;
		var end = start + 2;
		if (start == 0.00) {
			end = 0.00;
		}

		var startMin = this.json.minValue3;
		var endMin = startMin + 2;
		if (startMin == 0.00) {
			endMin = 0.00;
		}

		var startMax = this.json.maxValue3;
		var endMax = startMax + 2;
		var year = this.currentAcademicYear;

		jQuery("#chart3").kendoChart({
			dataSource: {
				transport: {
					read: {
						url: "../person/getWorkTypeBarchart/3/" + year,
						dataType: "json"
					}
				}
			},
			title: {

				text: "ระดับคะแนนในภาควิชา ด้านงานวิจัย  ค่่าเฉลี่ย  " + this.json.mean3 + "  "
				// text: "ระดับคะแนนในภาควิชา ด้านงานวิจัย  ค่่าเฉลี่ย  " + this.json.mean3 + "  (เกณฑ์ขั้นต่ำ:" + this.json.minDesc3 + "   เกณฑ์ขั้นสูง:" + this.json.maxDesc3 + ")"
			},
			series: [{
				type: "column",
				field: "axisValue",
				name: "ระดับคะแนน"
			}],
			categoryAxis: {
				field: "axisName",
				labels: {
					rotation: -90
				}
			}, valueAxis: {
				plotBands: [
					{ from: start, to: end, color: "orange" },
					{ from: startMax, to: endMax, color: "green" },
					{ from: startMin, to: endMin, color: "red" }
				]
			}
			,
			tooltip: {
				visible: true,
				template: "#= series.name #: #= value #"
			}
		});


	}
    public kendoChart4() {

		var start = this.json.mean4;
		var end = start + 2;
		if (start == 0.00) {
			end = 0.00;
		}

		var startMin = this.json.minValue4;
		var endMin = startMin + 2;
		if (startMin == 0.00) {
			endMin = 0.00;
		}

		var startMax = this.json.maxValue4;
		var endMax = startMax + 2;
		var year = this.currentAcademicYear;

		jQuery("#chart4").kendoChart({
			dataSource: {
				transport: {
					read: {
						url: "../person/getWorkTypeBarchart/4/" + year,
						dataType: "json"
					}
				}
			},
			title: {
				text: "ระดับคะแนนในภาควิชา ด้านงานบริการวิชาการ  ค่่าเฉลี่ย  " + this.json.mean4 + "  "
// text: "ระดับคะแนนในภาควิชา ด้านงานบริการวิชาการ  ค่่าเฉลี่ย  " + this.json.mean4 + "  (เกณฑ์ขั้นต่ำ:" + this.json.minDesc4 + "   เกณฑ์ขั้นสูง:" + this.json.maxDesc4 + ")"

			},
			series: [{
				type: "column",
				field: "axisValue",
				name: "ระดับคะแนน"
			}],
			categoryAxis: {
				field: "axisName",
				labels: {
					rotation: -90
				}
			}, valueAxis: {
				plotBands: [
					{ from: start, to: end, color: "orange" },
					{ from: startMax, to: endMax, color: "green" },
					{ from: startMin, to: endMin, color: "red" }
				]
			}
			,
			tooltip: {
				visible: true,
				template: "#= series.name #: #= value #"
			}
		});


	}
	public kendoChart5() {

		var start = this.json.mean5;
		var end = start + 2;
		if (start == 0.00) {
			end = 0.00;
		}

		var startMin = this.json.minValue5;
		var endMin = startMin + 2;
		if (startMin == 0.00) {
			endMin = 0.00;
		}

		var startMax = this.json.maxValue5;
		var endMax = startMax + 2;
		var year = this.currentAcademicYear;


		jQuery("#chart5").kendoChart({
			dataSource: {
				transport: {
					read: {
						url: "../person/getWorkTypeBarchart/5/" + year,
						dataType: "json"
					}
				}
			},
			title: {
				// text: "ระดับคะแนนในภาควิชา ด้านงานทำนุบำรุงศิลป  ค่่าเฉลี่ย  " + this.json.mean5 + "  (เกณฑ์ขั้นต่ำ:" + this.json.minDesc5 + "   เกณฑ์ขั้นสูง:" + this.json.maxDesc5 + ")"
text: "ระดับคะแนนในภาควิชา ด้านงานทำนุบำรุงศิลป  ค่่าเฉลี่ย  " + this.json.mean5 + "  "
			},
			series: [{
				type: "column",
				field: "axisValue",
				name: "ระดับคะแนน"
			}],
			categoryAxis: {
				field: "axisName",
				labels: {
					rotation: -90
				}
			}, valueAxis: {
				plotBands: [
					{ from: start, to: end, color: "orange" },
					{ from: startMax, to: endMax, color: "green" },
					{ from: startMin, to: endMin, color: "red" }
				]
			}
			,
			tooltip: {
				visible: true,
				template: "#= series.name #: #= value #"
			}
		});



	}


	public changeYear(year: any) {

        this.DepartmentName(year);
    }

}


