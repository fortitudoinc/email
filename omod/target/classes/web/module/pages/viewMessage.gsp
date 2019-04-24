<% ui.decorateWith("appui", "standardEmrPage") %>
<script type="text/javascript">
            //var OPENMRS_CONTEXT_PATH = 'openmrs';
            window.sessionContext = window.sessionContext || {
                locale: "en_GB"
            };
            window.translations = window.translations || {};
</script>

<script type="text/javascript">
   var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "View All Messages", link: '/' + OPENMRS_CONTEXT_PATH + "/internlmsgs/viewAllMessages.page"},
        { label: "View Message"}
    ];
</script>

<br><br>



<script type="text/javascript">
    jq=jQuery;
    jq(function() { 
        if ("$replyForwardDraft" === "draft") {
            document.getElementById("draftDiv").style.display = "block";
            document.getElementById("replyDiv").style.display = "none";
        } else {
            document.getElementById("replyDiv").style.display = "block";
            document.getElementById("draftDiv").style.display = "none";
        }
 var x = `$msg.message.msgBody`;               
        jq("#recipients").val("$msg.recipientsNames");
        var replyForward = "/" + OPENMRS_CONTEXT_PATH + 
            "/internlmsgs/msgReplyForward.page?messageId=$msg.message.id&action=";
        jq("#reply").attr("href",replyForward + "reply" );
        jq("#replyAll").attr("href",replyForward + "replyAll" );
        jq("#forward").attr("href",replyForward + "forward" );  
        jq("#draft").attr("href",replyForward + "draft" );  
        jq("#returnURL").val("/" + OPENMRS_CONTEXT_PATH + "/internlmsgs/viewAllMessages.page");
    });

</script>


<div id="draftDiv" hidden>
           <a href="" id="draft">Continue with Draft/</a>
           <a href="viewAllMessages.page">&nbsp;&nbsp;&nbsp;Close</a>
</div>
<div id="replyDiv" hidden>
            <a href="" id="reply">Reply</a>/
            <a href="" id="replyAll">Reply All</a>/
            <a href="" id="forward">Forward</a>/
            <a href="viewAllMessages.page">Close</a>
</div>
<br><br>

<form id="deleteMessageForm" method="post">
<div  id="fromDiv">
FROM: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$msg.senderName
<br>
</div>

<div  id="viewMessageRecipientsDiv">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To: <input id="recipients" size="100"  name="recipientNames" readonly/><br>
</div>

<div  id="viewSubjectDiv">
Subject: <textarea rows="1" cols="50" id="subject" name="subject" readonly>$msg.message.msgSubject</textarea><br>
</div>


<div  id="viewBodyDiv">
<textarea rows="10" cols="80" style="pre-wrap" id="body" name="body" readonly>$msg.message.msgBody</textarea><br>
</div>

<form id="deleteMessageForm" method="post">
<div class="fields" id="messageIdDiv">
<input type="hidden" name="messageIdd" value="$msg.message.id"/>
</div>


<div class="fields" id="returnURLDiv">
<input type="hidden" id="returnURL" name="returnUrl" />
</div>
<input type="submit" value="Delete Message">
</form>







<script type="text/javascript">
            //var OPENMRS_CONTEXT_PATH = 'openmrs';
            window.sessionContext = window.sessionContext || {
                locale: "en_GB"
            };
            window.translations = window.translations || {};
</script>
