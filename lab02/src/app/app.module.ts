import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { PlayerModule } from './player.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [PlayerModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
