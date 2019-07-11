import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VideoPlayerComponent } from '../app/component/video-player.component';
import { NewPlayItemComponent } from '../app/component/new-play-item';

const routes: Routes = [
  { path: "player", component: VideoPlayerComponent },
  { path: "new", component: NewPlayItemComponent },
  { path: "**", component: VideoPlayerComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
