export class ClientRecord {
  public id: string | undefined;
  public clientId: string | undefined;
  public title: string | undefined;
  public content: string | undefined;
  public sessionDate: Date | undefined;
  public createdDate: Date | undefined;
  public files: ClientFile[] = [];
}

export class ClientFile {
  public id: string | undefined;
  public clientRecordId: string | undefined;
  public name: string | undefined;
  public url: string | undefined;
}
