package cokr.xit.modules.kkomydoc.model;

import java.util.List;

import cokr.xit.modules.kkomydoc.model.child.Document;
import cokr.xit.modules.kkomydoc.model.child.NotiDocument;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BulkSendReqDTO {
	private List<Document> documents;
	private List<NotiDocument> notidocuments;
}
