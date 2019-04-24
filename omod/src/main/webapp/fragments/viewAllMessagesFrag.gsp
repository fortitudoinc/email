
       <style>
            body {
                font-family: "Lato", sans-serif;
            }

            .sidenav {
                height: 100%;
                width: 250;
                position: fixed;
                z-index: 1;
                margin-top: inherit;
                margin-left: inherit;
                background-color: white;
                overflow-x: hidden;
                transition: 0.5s;
                padding: 60px 5px 10px 5px;
            }

            .sidenav a {
                padding: 8px 4px 8px 5px;
                text-decoration: none;
                font-size: 15px;
                color: #818181;
                display: block;
                transition: 0.3s;
            }

            .sidenav a:hover, .offcanvas a:focus{
                color: #f1f1f1;
            }

            div.ex1 {
                margin-left: 80px;
            }


            .button {
                background-color: white;
                border: none;
                color: black;
                padding: 10px 12px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 10px;
                margin: 4px 2px;
                cursor: pointer;
            }


 .Table{
    height: 400px;
    overflow:auto;
}

.Tables {
    overflow-x:scroll;
}


.Tbody {
    max-height: 300px
    overflow-y:scroll;
    display:block;
}

        </style>



<script>
var emtrash;
var msgIdTrash;
var inboxDataTable;
var selectInboxDataTableRow;

var lastInboxRowSelected = null;
var lastInboxMessageId;
var lastInboxSenderName;
var lastInboxSubject;
var lastInboxDate;
var lastInboxBody;
var lastInboxRecipientNames;

var lastSentRowSelected = null;
var lastSentMessageId;
var lastSentRowSelected = null;
var lastSentSenderName;
var lastSentSubject;
var lastSentDate;
var lastSentBody;
var lastSentRecipientNames;

var lastDraftRowSelected = null;
var lastDraftMessageId;
var lastDraftSenderName;
var lastDraftSubject;
var lastDraftDate;
var lastDraftBody;
var lastDraftRecipientNames;


var lastTrashRowSelected = null;
var lastTrashMessageId;
var lastTrashSenderName;
var lastTrashSubject;
var lastTrashDate;
var lastTrashBody;
var lastTrashRecipientNames;



jq = jQuery;
jq(function() { 



    jq("#deleteAllMessagesInTrash").click(function() {
        jq("#deleteRestoreTrashMsgId").val(0);
        jq("#deleteRestoreTrash").val("deleteAll");
        jq( "#dialog-confirm-allmessages" ).dialog( "open" );
    });

jq( "#dialog-confirm-allmessages" ).dialog({
    autoOpen: false,
      resizable: false,
      height: "auto",
      width: 400,
      modal: true,
      buttons: {
        "Delete all messages": function() {
            jq(this).dialog("close");
            callbackDeleteAllMessages(true);
        },
        Cancel: function() {
            jq(this).dialog("close");
            callbackDeleteAllMessages(false);
        }
      }
    });

jq( "#dialog-confirm-amessage" ).dialog({
    autoOpen: false,
      resizable: false,
      height: "auto",
      width: 400,
      modal: true,
      buttons: {
        "Delete the Message": function() {
            jq(this).dialog("close");
            callbackDeleteMessage(true);
        },
        Cancel: function() {
            jq(this).dialog("close");
            callbackDeleteMessage(false);
        }
      }
    });



      var composeLink = "/" + OPENMRS_CONTEXT_PATH + "/internlmsgs/composeMessage.page";
      jq("#newMessageLink").attr("href",composeLink);
      jq("#newMessageLink")[0].style.fontSize="12px";
      jq("#newMessageLink")[0].style.color="red";


});
</script>

        <script>
            function openNav() {
                document.getElementById("mySidenav").style.width = "250px";
                document.getElementById("mainDiv").style.marginLeft = "250px";
            }


            function openInbox() {
                document.getElementById("inboxDiv").style.display = "block";
                document.getElementById("sentDiv").style.display = "none";
                document.getElementById("trashDiv").style.display = "none";
                document.getElementById("draftDiv").style.display = "none";

                if (lastInboxRowSelected == null) {
                    document.getElementById("viewMessageDiv").style.display = "none";
                    return;
                }

                fillinMessage(lastInboxSenderName,lastInboxSubject,lastInboxDate,lastInboxBody,lastInboxRecipientNames);
            }

            function openDraft() {
                document.getElementById("inboxDiv").style.display = "none";
                document.getElementById("sentDiv").style.display = "none";
                document.getElementById("trashDiv").style.display = "none";
                document.getElementById("draftDiv").style.display = "block";

                if (lastDraftRowSelected == null) {
                    document.getElementById("viewMessageDiv").style.display = "none";
                    return;
                }
                fillinMessage(lastDraftSenderName,lastDraftSubject,lastDraftDate,lastDraftBody,lastDraftRecipientNames);
            }

            function openSent() {
                document.getElementById("inboxDiv").style.display = "none";
                document.getElementById("sentDiv").style.display = "block";
                document.getElementById("trashDiv").style.display = "none";
                document.getElementById("draftDiv").style.display = "none";


                if (lastSentRowSelected == null) {
                    document.getElementById("viewMessageDiv").style.display = "none";
                    return;
                }
                fillinMessage(lastSentSenderName,lastSentSubject,lastSentDate,lastSentBody,lastSentRecipientNames);
            }

            function openTrash() {
                document.getElementById("inboxDiv").style.display = "none";
                document.getElementById("sentDiv").style.display = "none";
                document.getElementById("trashDiv").style.display = "block";
                document.getElementById("draftDiv").style.display = "none";


                if (lastTrashRowSelected == null) {
                    document.getElementById("viewMessageDiv").style.display = "none";
                    return;
                }
                fillinMessage(lastTrashSenderName,lastTrashSubject,lastTrashDate,lastTrashBody,lastTrashRecipientNames);
            }



            function closeNav() {
                document.getElementById("mySidenav").style.width = "0";
            }


            function callbackDeleteAllMessages(value) {
                var row;

                var td;

                event.stopPropagation();
                if (!value) {
                    return;
                }

                jq('#trashTable tbody tr').each(function(){
                    row = this;

                    td = row.cells[0];
                    emtrash = td.getElementsByTagName('em')[0];
                    if (typeof emtrash  === 'undefined') {
                        return;
                    };
                    callbackDeleteMessage(true);
                })
            }

            function callbackDeleteMessage(value) {
                var onclickElement;
                var msgId, msgLen, msgIdStr, msgIdStrWithQuotes, msgIdStrArr;
                event.stopPropagation();
                if (!value) {
                    return;
                }
               
                var row = emtrash.closest("tr");
                if (lastTrashRowSelected === row) {
                    document.getElementById("viewMessageDiv").style.display = "none";
                    lastTrashRowSelected = null;
                }
                    
                var tbody = emtrash.closest("tbody");
                tbody.removeChild(row);

                    onclickElement = row.getAttribute('onclick');
                    msgIdStrArr = onclickElement.match(/'[0-9]*'/);
                    msgIdStrWithQuotes = msgIdStrArr[0];
                    msgLen = msgIdStrWithQuotes.length;
                    msgIdStr = msgIdStrWithQuotes.substring(1, msgLen-1);
                    msgId = Number(msgIdStr);

                jq.getJSON('${ ui.actionLink("deleteMessage") }',
                    {
                      'messageId': msgId
                    })
                    .error(function(xhr, status, err) {
                        alert('AJAX error ' + err);
                    })
                    .success(function() {
                        emr.successMessage("Message Deleted");
                    });
            }

           function restoreMail(event,em,msgId,msgTag) {
                event.stopPropagation();

                var row = em.closest("tr");
                if (lastTrashRowSelected === row) {
                    document.getElementById("viewMessageDiv").style.display = "none";
                    lastTrashRowSelected = null;
                    removeRowHilite(row);
                }
                var tbody = em.closest("tbody");
                tbody.removeChild(row);

                jq.getJSON('${ ui.actionLink("restoreMessage") }',
                    {
                      'messageId': msgId
                    })
                .success(function() {
                var tableId;
                if (msgTag.startsWith("recip")) {
                    tableId = "inboxTable";}
                    else if (msgTag.startsWith("sent")) {
                    tableId = "sentTable"}
                    else if (msgTag.startsWith("draft"))
                    {tableId = "draftTable"}
                     else {tableId = "trashTable" }


                cloneRow(row,tableId,msgId,msgTag);
                emr.successMessage("Message Restored");
                });
  
            }

            function trashMail(event,em,msgId,msgTag) {
                event.stopPropagation();
                var row = em.closest("tr");
                var tbody = em.closest("tbody");
                tbody.removeChild(row);
                adjustMailDisplayAndHilite(row, msgTag);

                jq.getJSON('${ ui.actionLink("trashMessage") }',
                    {
                      'messageId': msgId
                    })
                .success(function() {

                    emr.successMessage("Message Trashed");
                    cloneRow(row,"trashTable",msgId,msgTag);
                });

            }

            function adjustMailDisplayAndHilite(row, msgTag) {
                if (row.style.backgroundColor === "") {
                    return;
                }
                row.style.backgroundColor = "";
                document.getElementById("viewMessageDiv").style.display = "none";
                if (msgTag.startsWith("recip")) {
                    lastInboxRowSelected = null;
                } else if (msgTag.startsWith("draft")) {
                    lastDraftRowSelected = null;
                } else {
                    lastSentRowSelected = null;
                }
            }

            function cloneRow(row,tableId,msgId,msgTag) {
                var table = document.getElementById(tableId); // find table to append to
                var newRow = row;
                var nn = row.cloneNode(true); // copy children too
                var newEm1;
                var newEm2;
                var onclickElement = newRow.getAttribute('onclick');
                
                var body = table.tBodies[0];
                var td = newRow.cells[0];
                if (tableId === "trashTable") {
                    onclickElement = "selectMessageTrash" + onclickElement.substring(onclickElement.indexOf("("));

                    td.removeChild(td.childNodes[0]);
                    newEm1 = document.createElement('em');
                    newEm1.setAttribute('onclick', "restoreMail(event,this, " + msgId + "," + "'" + msgTag + "'" +")");
                    newEm1.setAttribute("class","icon-chevron-left delete-action");
                    newEm1.setAttribute("title","Restore Mail");
                    td.appendChild(newEm1);
                

                    newEm2 = document.createElement('em');
                    newEm2.setAttribute('onclick', "deleteMail(this," + msgId +")");
                    newEm2.setAttribute("class","icon-trash delete-action");
                    newEm2.setAttribute("title","Delete Mail");
                    td.appendChild(newEm2);
                    body.appendChild(newRow); // add new row to end of table

                }
                else { // remove both em nodes since we're restoring row from trash
                    var targetSelect;
                    if (tableId === "inboxTable") {
                        targetSelect = "selectMessageInbox";
                    } else if (tableId === "draftTable") {
                        targetSelect = "selectMessageDraft";
                    } else {
                        targetSelect = "selectMessageSent";
                    }
                    onclickElement = targetSelect + onclickElement.substring(onclickElement.indexOf("("));
                    newRow.deleteCell(0);
                    td = newRow.insertCell(0);
                    newEm2 = document.createElement('em');
                    newEm2.setAttribute('onclick', "trashMail(event,this," + msgId + "," + "'" + msgTag + "'" +")");
                    newEm2.setAttribute("class","icon-trash delete-action");
                    newEm2.setAttribute("title","Trash Mail");
                    td.appendChild(newEm2);
                    body.appendChild(newRow); // add new row to end of table
                }
                newRow.setAttribute("onclick",onclickElement);
                removeRowHilite(newRow);
            }


           function deleteMail(em,msgId) {
                emtrash = em;
                msgIdTrash = msgId;
                jq("#deleteRestoreTrashMsgId").val(msgId);      
                jq("#deleteRestoreTrash").val("delete");
               jq( "#dialog-confirm-amessage" ).dialog( "open" );
            }

            function selectMessage(msgId) {
                window.location.href = "viewMessage.page?messageId=" + msgId + "&replyForwardDraft=replyforward";
            }

            function selectDraftMessage(msgId) {
                window.location.href = "viewMessage.page?messageId=" + msgId + "&replyForwardDraft=draft";
            }


            function selectMessageInbox(row,msgId,senderName,subject,date,body,recipientsNames) {
                document.getElementById("viewMessageDiv").style.display = "block";
                removeRowHilite(lastInboxRowSelected);
                lastInboxRowSelected = row;
                hiliteRow(row);
                fillinMessage(senderName,subject,date,body,recipientsNames);
                recordInboxMsgInfo(msgId,senderName,subject,date,body,recipientsNames);
                document.getElementById("replyDiv").style.display = "block";
                document.getElementById("continueDraftDiv").style.display = "none";
                setReplyForward(msgId);
            } 
            function setReplyForward(msgId) {
                    var replyForward = "/" + OPENMRS_CONTEXT_PATH + 
                        "/internlmsgs/msgReplyForward.page?messageId=" + msgId + "&action=";
                    jq("#reply").attr("href",replyForward + "reply" );
                    jq("#replyAll").attr("href",replyForward + "replyAll" );
                    jq("#forward").attr("href",replyForward + "forward" ); 
            }

            function selectMessageDraft(row,msgId,senderName,subject,date,body,recipientsNames) {
                document.getElementById("viewMessageDiv").style.display = "block";
                removeRowHilite(lastDraftRowSelected);
                lastDraftRowSelected = row;
                hiliteRow(row);
                fillinMessage(senderName,subject,date,body,recipientsNames);
                recordDraftMsgInfo(msgId,senderName,subject,date,body,recipientsNames);
                document.getElementById("replyDiv").style.display = "none";
                document.getElementById("continueDraftDiv").style.display = "block";
                var replyForward = "/" + OPENMRS_CONTEXT_PATH + 
                        "/internlmsgs/msgReplyForward.page?messageId=" + msgId + "&action=draft";
                jq("#draft").attr("href",replyForward );
            } 
            
            function selectMessageSent(row,msgId,senderName,subject,date,body,recipientsNames) {
                document.getElementById("viewMessageDiv").style.display = "block";
                removeRowHilite(lastSentRowSelected);
                lastSentRowSelected = row;
                hiliteRow(row);
                fillinMessage(senderName,subject,date,body,recipientsNames);
                recordSentMsgInfo(msgId,senderName,subject,date,body,recipientsNames);
                document.getElementById("replyDiv").style.display = "block";
                document.getElementById("continueDraftDiv").style.display = "none";
                setReplyForward(msgId);
            } 

            function selectMessageTrash(row,msgId,senderName,subject,date,body,recipientsNames) {
                document.getElementById("viewMessageDiv").style.display = "block";
                removeRowHilite(lastTrashRowSelected);
                lastTrashRowSelected = row;
                hiliteRow(row);
                fillinMessage(senderName,subject,date,body,recipientsNames);
                recordTrashMsgInfo(msgId,senderName,subject,date,body,recipientsNames);
                document.getElementById("replyDiv").style.display = "none";
                document.getElementById("continueDraftDiv").style.display = "none";
            } 
            function removeRowHilite(row) {
                if (row == null) {
                    return;
                }
                row.style.backgroundColor = "";
            }

            function hiliteRow(row) {   
                //row.style.backgroundColor = "cyan";
                row.style.backgroundColor = "green";
            }

            function fillinMessage(senderName,subject,date,body,recipientsNames) {
                jq("#from").html(senderName);
                jq("#recipients").val(recipientsNames);
                jq("#subject").val(subject);
                jq("#body").val(body);
                document.getElementById("viewMessageDiv").style.display = "block";
            }

            function recordInboxMsgInfo(msgId,senderName,subject,date,body,recipientsNames) {
                lastInboxMessageId = msgId;
                lastInboxSenderName = senderName;
                lastInboxSubject = subject;
                lastInboxDate = date;
                lastInboxBody = body;
                lastInboxRecipientNames = recipientsNames;
            }

            function recordDraftMsgInfo(msgId,senderName,subject,date,body,recipientsNames) {
                lastDraftMessageId = msgId;
                lastDraftSenderName = senderName;
                lastDraftSubject = subject;
                lastDraftDate = date;
                lastDraftBody = body;
                lastDraftRecipientNames = recipientsNames;
            }

            function recordSentMsgInfo(msgId,senderName,subject,date,body,recipientsNames) {
                lastSentMessageId = msgId;
                lastSentSenderName = senderName;
                lastSentSubject = subject;
                lastSentDate = date;
                lastSentBody = body;
                lastSentRecipientNames = recipientsNames;
            }

            function recordTrashMsgInfo(msgId,senderName,subject,date,body,recipientsNames) {
                lastTrashMessageId = msgId;
                lastTrashSenderName = senderName;
                lastTrashSubject = subject;
                lastTrashDate = date;
                lastTrashBody = body;
                lastTrashRecipientNames = recipientsNames;
            }

        </script>







        <div id="mySidenav" class="sidenav">
            <a target="_blank" id="newMessageLink" >Compose</a>
            <a href="#" onclick="openInbox()">Inbox</a>
            <a href="#" onclick="openDraft()">Draft</a>
            <a href="#" onclick="openSent()">Sent</a>
            <a href="#" onclick="openTrash()">Trash</a>
            <a href="#" onclick="location.reload(true)">Refresh</a>
        </div>

        <div id="mainDiv" class="ex1">


<div hidden>
            <h2>Side Navigation Example</h2>
            This side will contain the content<br>
            which is one of inbox, etc.<br><br><br>
 
<% if (context.authenticated) { %>
<br><br>
USER ID: $context.authenticatedUser.userId
<br><br>
    And a special hello to you, $context.authenticatedUser.personName.fullName.<br>
*****User id:context.authenticatedUser.userId $context.authenticatedUser.userId<br>
    Your roles are:
    <% context.authenticatedUser.roles.findAll { !it.retired }.each { %>
        $it.role ($it.description)
    <% } %>
<% } else { %>
    You are not logged in.
<% } %>


</div>





<div id="dialog-confirm-allmessages" title="Delete All Messsages?">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>These items will be permanently deleted and cannot be recovered. Are you sure?</p>
</div>

<div id="dialog-confirm-amessage" title="Delete a Message?">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>This message will be permanently deleted and cannot be recovered. Are you sure?</p>
</div>


<br><br>
<br><br>



<div class="Table" id="allMessages" hidden>
ALL MESSAGES
<table id="allTable"  border="1" class="display" cellspacing="0" width="50%">
<thead>
  <tr>
   <th>Sender Id</th>
   <th>Message Id</th> <th>Date</th><th>draft</th><th>trashed</th><th>deleted</th>
    <th>Recip Ids</th>
  </tr>
</thead>
<tbody >
  <% if (messages) { %>
     <% messages.each { %>
      <tr>
<td>${ ui.format(it.message.senderUserId)}</td>
<td onclick="selectMessage($it.message.id)">${ ui.format(it.message.id)}</td>
<td>${ ui.format(it.message.msgDate)}</td>
<td>${ ui.format(it.message.isDraft)}</td>
<td>${ ui.format(it.message.isTrashed)}</td>
<td>${ ui.format(it.message.isDeleted)}</td>
<td>${ ui.format(it.message.msgRecipients)}</td>
      </tr>
    <% } %>
  <% } else { %>
  <tr>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
  </tr>
  <% } %>
</tbody>
</table>
</div>
<br><br>







<div id="inboxDiv">
INBOX
<br><br>

<div class="Table" >
<table id="inboxTable"  border="1" class="display" cellspacing="0" width="50%">
<thead>
  <tr>
   <th></th>
   <th>From</th><th>subject</th><th>Date</th><th>Recipients</th>
  </tr>
</thead>
<tbody>
  <% if (receivedMail) { %>
     <% receivedMail.each { %>
      <tr onclick="selectMessageInbox(this,'$it.message.id','$it.senderName','$it.message.msgSubject','$it.message.msgDate',
    `$it.message.msgBody`,'$it.recipientsNames')">
<td><em class="icon-trash delete-action" title="Trash Mail"  onclick="trashMail(event,this,$it.message.id,'$it.message.msgTag')"></em></td>
<td >${ ui.format(it.senderName)}</td>
<td>${ ui.format(it.message.msgSubject)}</td>
<td>${ ui.format(it.message.msgDate)}</td>
<td>${ ui.format(it.recipientsNames)}</td>
      </tr>
    <% } %>
  <% } else { %>
  <tr>
   <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
  </tr>
  <% } %>
</tbody>
</table>
</div>
</div>




<div id="draftDiv" hidden>
DRAFT MAIL
<br><br>

<div class="Table" >

<table id="draftTable"  border="1" class="display" cellspacing="0" width="50%">
<thead>
  <tr>
<th></th>
   <th>From</th><th>subject</th><th>Date</th><th>Recipients</th>
  </tr>
</thead>
<tbody>
  <% if (draftMail) { %>
     <% draftMail.each { %>
      <tr onclick="selectMessageDraft(this,'$it.message.id','$it.senderName','$it.message.msgSubject','$it.message.msgDate',
    `$it.message.msgBody`,'$it.recipientsNames')">
<td><em class="icon-trash delete-action" title="Trash Mail"  onclick="trashMail(event,this,$it.message.id,'$it.message.msgTag')"></em></td>
<td >${ ui.format(it.senderName)}</td>
<td>${ ui.format(it.message.msgSubject)}</td>
<td>${ ui.format(it.message.msgDate)}</td>
<td>${ ui.format(it.recipientsNames)}</td>
      </tr>
    <% } %>
  <% } else { %>
  <tr>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
  </tr>
  <% } %>
</tbody>
</table>
</div>
</div>







<div id="sentDiv" hidden>
SENT MAIL
<br><br>

<div class="Table">
<table id="sentTable"  border="1" class="display" cellspacing="0" width="50%">
<thead>
  <tr>
<th></th>
   <th>From</th><th>subject</th><th>Date</th><th>Recipients</th>
  </tr>
</thead>
<tbody>
  <% if (sentMail) { %>
     <% sentMail.each { %>
 <tr onclick="selectMessageSent(this,'$it.message.id','$it.senderName','$it.message.msgSubject','$it.message.msgDate',
    `$it.message.msgBody`,'$it.recipientsNames')">
<td><em class="icon-trash delete-action" title="Trash Mail"  onclick="trashMail(event,this,$it.message.id,'$it.message.msgTag')"></em></td>
<td >${ ui.format(it.senderName)}</td>
<td>${ ui.format(it.message.msgSubject)}</td>
<td>${ ui.format(it.message.msgDate)}</td>
<td>${ ui.format(it.recipientsNames)}</td>
      </tr>
    <% } %>
  <% } else { %>
  <tr>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
  </tr>
  <% } %>
</tbody>
</table>
</div>
</div>






<div id="trashDiv" hidden>
TRASH MAIL &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" id="deleteAllMessagesInTrash">Delete All Items in Trash</button>
<br><br>
<div class="Table">
<table id="trashTable"  border="1" class="display" cellspacing="0" width="50%">
<thead>
  <tr>
<th></th>
   <th>From</th><th>subject</th><th>Date</th><th>Recipients</th>
  </tr>
</thead>
<tbody>
  <% if (trashMail) { %>
     <% trashMail.each { %>
 <tr onclick="selectMessageTrash(this,'$it.message.id','$it.senderName','$it.message.msgSubject','$it.message.msgDate',
    `$it.message.msgBody`,'$it.recipientsNames')">
<td>
<em class="icon-chevron-left delete-action" title="Restore Mail"  onclick="restoreMail(event,this,$it.message.id,'$it.message.msgTag')"></em>
<em class="icon-trash delete-action" title="Delete Mail"  onclick="deleteMail(this,$it.message.id)"></em>
</td>
<td>${ ui.format(it.senderName)}</td>
<td>${ ui.format(it.message.msgSubject)}</td>
<td>${ ui.format(it.message.msgDate)}</td>
<td>${ ui.format(it.recipientsNames)}</td>
      </tr>
    <% } %>
  <% } else { %>
  <tr>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
  </tr>
  <% } %>
</tbody>
</table>

</div>










<div class="fields" id="deleteRestoreTrashMsgIdDiv">
<input type="hidden" id="deleteRestoreTrashMsgId" name="deleteRestoreTrashMsgId" />
</div>

<div class="fields" id="deleteAllMsgIdsDiv">
<input type="hidden" id="deleteAllMsgIds" name="deleteAllMsgIds" />
</div>

<div class="fields" id="deleteRestoreTrashDiv">
<input type="hidden" id="deleteRestoreTrash" name="deleteRestoreTrash" value="none"/>
</div>




<script type="text/javascript">

jq("table tbody tr").click(function() {

var row = jq('table > tbody > tr.highlight');
row.removeClass("highlight");
row.find('td').each (function( column, td) {
    jq(td).removeClass('highlight');
});

    jq(this).addClass('highlight');
    jq(this).find('td').each (function( column, td) {
    jq(td).addClass('highlight');
});  
});


function f() {

var selectedTd = jq('#testtable tr').eq(130).find('td').eq(0);
    var selected = jq(this).hasClass("highlight");
  var index = jq( "td" ).index( this );
};

</script>





</div>


<div id="viewMessageDiv" hidden>

<br><br>
<div id="replyDiv" hidden>
            <a target="_blank" href="" id="reply">Reply</a>/
            <a target="_blank" href="" id="replyAll">Reply All</a>/
            <a target="_blank" href="" id="forward">Forward</a>/
</div>

<div id="continueDraftDiv" hidden>
<a target="_blank" href="" id="draft">Continue Draft</a>/
</div>
<br><br>



<div  id="fromDiv" >
FROM: &nbsp;<span id="from"></span>
<br>
</div>

<div  id="viewMessageRecipientsDiv">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To: <input id="recipients" size="100"  name="recipientNames" readonly/><br>
</div>

<div  id="viewSubjectDiv">
Subject: <textarea rows="1" cols="50" id="subject" name="subject" readonly></textarea><br>
</div>


<div  id="viewBodyDiv">
<textarea rows="10" cols="80" style="pre-wrap" id="body" name="body" readonly></textarea><br>
</div>

</div>

</div>