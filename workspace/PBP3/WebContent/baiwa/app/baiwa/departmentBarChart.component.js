"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var http_1 = require('@angular/http');
var departmentBarChart = (function () {
    function departmentBarChart(http) {
        this.http = http;
    }
    departmentBarChart.prototype.ngOnInit = function () {
        this.getUserName();
    };
    departmentBarChart.prototype.getDepartment = function () {
        var _this = this;
        var url = "../dean/initDepartmentBarChart";
        return this.http.get(url).subscribe(function (response) { return _this.GetkendoSucess(response); }, function (error) { return _this.GetDepartmentNameError(error); }, function () { return console.log("DepartmentName !"); });
    };
    departmentBarChart.prototype.getUserName = function () {
        var _this = this;
        var url = "../person/getUserSession";
        return this.http.get(url).subscribe(function (response) { return _this.GetUserNameSucess(response); }, function (error) { return _this.GetDepartmentNameError(error); }, function () { return console.log("DepartmentName !"); });
    };
    departmentBarChart.prototype.GetUserNameSucess = function (response) {
        this.jsonUser = response.json(JSON.stringify(response._body));
        console.log("this.jsonUser", this.jsonUser);
        this.academicYear = this.jsonUser.currentAcademicYear;
        this.academicYearList = this.jsonUser.academicYearList;
        this.getDepartment();
    };
    departmentBarChart.prototype.GetkendoSucess = function (response) {
        this.json = response.json(JSON.stringify(response._body));
        this.nameDepart = this.json.resObj.faculty;
        console.log("this.json", this.json);
        //this.mean1 = this.json.mean1;
        // this.departmentname = this.nameDepart[0].departmentDesc
        this.headDepart = this.jsonUser.departmentName;
        this.createChart(this.headDepart, this.academicYear);
    };
    departmentBarChart.prototype.GetDepartmentNameError = function (error) {
        console.log("GetDepartmentNameError.");
    };
    departmentBarChart.prototype.changSelection = function (value) {
        console.log("headDepart" + this.headDepart, "year" + this.academicYear);
        this.createChart(value, this.academicYear);
    };
    departmentBarChart.prototype.changSelectionYear = function (year) {
        console.log("headDepart" + this.headDepart, "year" + year);
        this.createChart(this.headDepart, year);
    };
    departmentBarChart.prototype.createChart = function (value, academicYear) {
        for (var i = 0; i < this.nameDepart.length; i++) {
            if (this.nameDepart[i].departmentDesc == value) {
                value = this.nameDepart[i].email;
            }
        }
        console.log("this.nameDepart", this.nameDepart);
        jQuery("#chart").kendoChart({
            dataSource: {
                transport: {
                    read: {
                        url: "../dean/getDepartmentBarchart/" + value + "/1/" + academicYear,
                        dataType: "json"
                    }
                }
            },
            chema: {
                data: function (response) {
                    for (var i = 0; i < response.length; i++) {
                        response[i].orderNo = new Number(response[i].orderNo);
                    }
                    return response;
                }
            },
            title: {
                text: "ระดับคะแนนในภาควิชา"
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
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
        jQuery("#grid").kendoGrid({
            dataSource: {
                transport: {
                    read: {
                        url: "../dean/getDepartmentBarchart/" + value + "/1/" + academicYear,
                        dataType: "Json"
                    }
                }
            },
            chema: {
                data: function (response) {
                    for (var i = 0; i < response.length; i++) {
                        response[i].orderNo = new Number(response[i].orderNo);
                    }
                    return response;
                }
            },
            columns: [
                { field: "axisName", title: "บุคลากร" },
                { field: "axisValue", title: "คะแนน" }
            ]
        });
    };
    departmentBarChart = __decorate([
        core_1.Component({
            templateUrl: 'app/baiwa/html/departmentBarChart.component.html'
        }), 
        __metadata('design:paramtypes', [http_1.Http])
    ], departmentBarChart);
    return departmentBarChart;
}());
exports.departmentBarChart = departmentBarChart;
//# sourceMappingURL=departmentBarChart.component.js.map