import {Component} from 'angular2/core';

@Component({
    selector: 'my-title',
    template: '<h1>{{pagetitle}}</h1>'
})
export class MyTitle {

  public pagetitle : String = 'Angular2 Tic Tac Toe!!!';

}
