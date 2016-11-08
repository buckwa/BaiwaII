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
var ImagePreview = (function () {
    function ImagePreview(el, renderer) {
        this.el = el;
        this.renderer = renderer;
    }
    ImagePreview.prototype.ngOnChanges = function (changes) {
        var reader = new FileReader();
        var el = this.el;
        reader.onloadend = function (e) {
            el.nativeElement.src = reader.result;
        };
        if (this.image) {
            return reader.readAsDataURL(this.image);
        }
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Object)
    ], ImagePreview.prototype, "image", void 0);
    ImagePreview = __decorate([
        core_1.Directive({ selector: 'img[imgPreview]' }), 
        __metadata('design:paramtypes', [core_1.ElementRef, core_1.Renderer])
    ], ImagePreview);
    return ImagePreview;
}());
exports.ImagePreview = ImagePreview;
//# sourceMappingURL=image_preview.directive.js.map