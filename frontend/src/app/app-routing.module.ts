import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { VersionListPageComponent } from './pages/version-list-page/version-list-page.component';
import { MessagesPageComponent } from './pages/messages-page/messages-page.component';

const routes: Routes = [
  { path: '', component: VersionListPageComponent },
  { path: 'messages', component: MessagesPageComponent },
  { path: '**', component: NotFoundPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
