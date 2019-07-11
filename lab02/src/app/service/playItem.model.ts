export class PlayItem {
  constructor(
    public id: string = '',
    public title: string = 'no title',
    public url: string = '',
    public status: string = '',
    public approved: number = 0,
    public likes: number = 0,
    public unlikes: number = 0,
    public currentStatus: string = 'stopped',
    public exitplayprogress: number = 0
  ) { }
}
