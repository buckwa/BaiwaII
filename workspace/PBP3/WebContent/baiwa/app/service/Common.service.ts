import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';

declare var swal: any;
declare var contextPath: string;
declare var jQuery;
declare var messages: any;
declare var LOV_MASTER: any;
declare var PAGE_ROLE_LIST: Object[];

@Injectable()
export class CommonService {

	private loadingOpt: any = {
		message: '<div class="blockui-default-message" ><i class="fa fa-circle-o-notch fa-spin"></i><h6>Please Wait</h6></div>',
		overlayCSS: {
			background: 'rgba(24, 44, 68, 0.8)',
			opacity: 1,
			cursor: 'wait'
		},
		css: {
			width: '50%'
		},
		blockMsgClass: 'block-msg-default'
				};

	private SESSION_TIMEOUT_MSG:string = "Session timeout.";

	constructor(private http: Http) { }

	public alert(msg: string, title?: string) {
		const session_msg = this.SESSION_TIMEOUT_MSG;
		swal({
			title: (title) ? title : "",
			text: msg,
			type: "warning",
			showCancelButton: false,
			cancelButtonClass: "btn-default",
			confirmButtonClass: "btn-default",
			confirmButtonText: "OK",
			imageSize: "60x60",
			animation: false,
			closeOnConfirm: true,
			allowEscapeKey: false

		},
			function () {
				if(session_msg == msg){
					window.location.href = contextPath + "login.htm";
				}
			});
	}

	public confirm(msg: string, fnCallBack: Function) {
		swal({
			title: "",
			text: msg,
			type: "warning",
			showCancelButton: true,
			cancelButtonClass: "btn-default",
			confirmButtonClass: "btn-default",
			confirmButtonText: "No",
			cancelButtonText: "Yes",
			closeOnConfirm: true,
			allowEscapeKey: false
		},
			function (isOk) {
				fnCallBack(!isOk);
			});
	}


	public callService(controllerUrl: string, data: any) {
		var headers = new Headers();
		let url = contextPath + controllerUrl;
		const session_msg = this.SESSION_TIMEOUT_MSG;
		const general_msg = this.getMessage("ERR_CONNECTION_REFUSED");
		console.log("callService", url);
		headers.append('Content-Type', "application/json; charset=utf-8");
		return this.http.post(url, data).map(function (response) {
			let json = { "code": "1", "errorDesrciption": general_msg };
			let textResponse = response.text();
			try {
				json = JSON.parse(textResponse);
			} catch (error) {
				// window.location.href = contextPath + "login.htm";
				console.error("callService Error : JSON.parse" , error);
				if (-1 != textResponse.indexOf("login")) {
					json["errorDesrciption"] = session_msg;
				}
			}
			return json;
		});
	}

	// ------------------------------------- REPORT -------------------------------------
	private ww: any;
	public getPDFViewer(controllerUrl: string, data: any) {
		this.loading();
		//popup
		let urlViewer = contextPath + 'resources/pdfjs/viewer.html?file=';
		// console.log(this.ww);
		if (this.ww != undefined && !this.ww.closed) {
			this.ww.close();
		}
		this.ww = window.open(urlViewer, "pdf viewer", "menubar=no,status=no,titlebar=no,toolbar=no,width=800,height=600");

		//Ajax
		let headers = new Headers();
		let url = contextPath + controllerUrl;
		console.log("getPDFData", url);
		headers.append('Content-Type', "application/json; charset=utf-8");

		//Wait for popup ready
		jQuery(this.ww).ready(jQuery.proxy(function () {
			console.log("PoPUP OK");

			this.http.post(url, data).map(function (response) { return JSON.parse(response.text()); })
				.subscribe(response => this.searchSuccess(response),
				error => this.searchError(), () => console.log("done !")
				);

		}, this));


	}

	private searchSuccess(response: any) {
		// console.log(response);
		if (response.code == "0") {
			let raw = window.atob(response.responeData);
			let rawLength = raw.length;
			let array = new Uint8Array(new ArrayBuffer(rawLength));
			for (let i = 0; i < rawLength; i++) {
				array[i] = raw.charCodeAt(i);
			}
			try {
				this.ww.PDFView.open(array);
			} catch (error) {
				console.log("PDFView.open Error", error);
				this.ww.close();
			}

		} else {
			this.alert(response.errorDesrciption);
			this.ww.close();
		}
		this.unLoading();

	}
	private searchError() {
		this.unLoading();
		this.ww.close();
		this.alert("GET PDF Error");
	}
	// -------------------------------------END REPORT -------------------------------------

	public loading() {
		jQuery('body').block(this.loadingOpt);
	}

	public unLoading() {
		jQuery('body').unblock();
	}


	public isEmpty(obj: any): boolean {
		return jQuery.trim(obj) == "";
	}


	public putSessionStorage(key: string, value: any): void {
		// sessionStorage[key] = JSON.stringify(value);
		if (value) {
			sessionStorage.setItem(key, JSON.stringify(value));
		} else {
			sessionStorage.setItem(key, null);
		}
	}
	public getSessionStorage(key: string): any {
		if (jQuery.trim(sessionStorage.getItem(key)) == "") {
			return "";
		} else {
			return JSON.parse(sessionStorage.getItem(key));
		}
	}

	public getMessage(key: string) {
		let str = messages[key];
		// console.log(str,messages);
		if (!str) {
			str = "Undefine : " + key;
		}
		return str;
	}


	// ---------------------------- LOV----------------------------
	public getLovByType(lovtype: string): any[] {
		return LOV_MASTER[jQuery.trim(lovtype)];
	}


	//-------------------------ROLE--------------------------------
	public hasAuthority(role: string): boolean {

		for (let item of PAGE_ROLE_LIST) {
			if (role == item["role"]) return true;
		}
		return false;
	}

	//------------------------ GET ----------------------------------
	callGET(requestUrlGet: string): Observable<any> {
		console.log("callGET requestUrlGet: ", requestUrlGet);
		return this.http.get(contextPath + requestUrlGet)
			.map(this.extractData)
	}
	private extractData(res: Response) {
		let body = res.json();
		// console.log("extractData", res);
		// console.log("body", body);
		return body || null;
	}

}