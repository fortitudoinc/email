
<div class="info-section drugs">    
    <div class="info-header">
        <i class="icon-envelope"></i>
        <a href="#" id="checkMessages" target="_blank">Check Messages</a>
        
    </div>

</div>

<script type="text/javascript">
    jq = jQuery;
 
    jq(function() { 
        jq("#checkMessages").attr("href","/" + OPENMRS_CONTEXT_PATH + "/internlmsgs/viewAllMessages.page");
});
    
</script>