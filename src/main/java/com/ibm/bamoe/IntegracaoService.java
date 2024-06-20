package com.ibm.bamoe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

@ApplicationScoped
public class IntegracaoService {
 
    @Inject Mailer mail;   

    public void enviaEmail(Empregado empregado, String resultado)
    {
        String mensagem = empregado.getNome() + 
            ", seu notebook foi solicitado, segue os detalhes da requisição " + resultado;
        mail.send(Mail.withText(empregado.getEmail(), "Notebook Solicitado", mensagem));
    }

    private HttpURLConnection getHTTPConnection(String urlApp)
    {
        try {
            URL url = new URL(urlApp);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "bamoe:");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.setRequestProperty("Authorization", 
                "Basic YmFtb2U6RU9NVEErPiFraHUrIXEmdWImNjFicl5iIUE3R14yaDdZTFVCbn1eNzlVSHQ4XVNocls7Y2pvbVU+SG49YStqR3JdMlckbHY0K2tbVG0=");
            return con;                
        } catch (Exception e) {
            return null;
        }
    }

    public String solicitaNotebook()
    {
        try{
            HttpURLConnection con = getHTTPConnection("https://dev266372.service-now.com/api/sn_sc/servicecatalog/items/774906834fbb4200086eeed18110c737/add_to_cart");
            String body = "{ \"sysparm_quantity\": 1, \"variables\":{ \"optional_label\": \"Teste123\",\"acrobat\": true,\"photoshop\": "+ 
            "true,\"eclipse_ide\": true,\"Additional_software_requirements\": \"teste - adicional data\"} }";            

            // Adiciona o item ao Carrinho
            try(OutputStream os = con.getOutputStream()) 
            {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);			
            }           
            con.connect();
            int responseCode = con.getResponseCode();
            con.disconnect();
            if (responseCode==200)
            {
                //Checkou do carrinho
                con = getHTTPConnection("https://dev266372.service-now.com/api/sn_sc/servicecatalog/cart/checkout");
                con.connect();
                responseCode = con.getResponseCode();
                
                String resposta = "";
                // Ler a resposta (se necessário)
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) 
                {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    resposta = response.toString();
                }
                con.disconnect();
                return "notebook solicitado com sucesso: " + resposta;
            }
            
        }
        catch (Exception e)
        {
            return "Falha ao solicitar o notebook para o serviceNow: " + e.getMessage();
        }
        return "Chegou ao final";
    }

}
