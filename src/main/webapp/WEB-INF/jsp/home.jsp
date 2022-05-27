<html>
    <head>
        <title>GCP Campaign External Modeller Application</title>
    </head>
    <body>
    <%@page import="com.externalmodeller.gcp.model.TokenModel"%>
    <% 
        if (request.getAttribute("tokenModelObject") != null) {
            TokenModel token = (TokenModel) request.getAttribute("tokenModelObject");
    %>
 
    <h1>Token Info</h1>
    <div>Access Token : <%= token.getAccess_token() %></div>
    <div>Expires_In : <%= token.getExpires_in() %></div>
    <div>Refresh Token: <%= token.getRefresh_token() %></div>
     <form action="/refreshServlet" method="post">
    	<input type="submit" name="button1" value="Button1" />
	</form>
    <% 
        } else { 
    %>

    <h1>No Token found.</h1>
         
    <% } %>	
    
    </body>
</html>