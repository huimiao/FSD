import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule, NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import {
  faPlay, faPause, faStop, faPlus, faMinus, faVolumeUp,
  faVolumeMute, faThumbsUp, faThumbsDown, faEdit, faSave, faCheck, faTimes, faUndo
} from '@fortawesome/free-solid-svg-icons';

import { AppComponent } from './app.component';
import { VideoPlayerComponent } from './component/video-player.component';
import { PlayerComponent, PlayerControllersComponent, PlayListComponent, CommonHeaderComponent } from './component/video-player';
import { NewPlayItemComponent } from './component/new-play-item';
import { ModelModule } from './service/model.module';

import { AppRoutingModule } from './app-routing.module';
import { FormsModule, FormBuilder, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [ VideoPlayerComponent, PlayerComponent, PlayerControllersComponent, PlayListComponent,
    CommonHeaderComponent, NewPlayItemComponent
  ],
  imports: [
    BrowserModule,
    FontAwesomeModule,
    ModelModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [NgbActiveModal, NgbModal, FormBuilder],
})
export class PlayerModule{
  constructor() {
    library.add(faPlay);
    library.add(faPause);
    library.add(faStop);
    library.add(faPlus);
    library.add(faMinus);
    library.add(faVolumeUp);
    library.add(faVolumeMute);
    library.add(faThumbsUp);
    library.add(faThumbsDown);
    library.add(faSave);
    library.add(faEdit);
    library.add(faCheck);
    library.add(faTimes);
    library.add(faUndo);
  }
}
