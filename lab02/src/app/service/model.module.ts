import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import { RestDataSource } from './rest.datasource';

@NgModule({
  imports: [HttpClientModule],
  providers: [RestDataSource]
})
export class ModelModule {

}
