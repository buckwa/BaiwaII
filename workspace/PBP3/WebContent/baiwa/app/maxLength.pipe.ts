import {Pipe} from '@angular/core';
 
@Pipe({
    name: 'maxLength'
})
export class MaxLengthPipe {
    transform(val, args:number) {
        if (val.length > 30){
            return val.substring(0, 30 )+'...';    
        }else{
            return val;
        }
    }
}