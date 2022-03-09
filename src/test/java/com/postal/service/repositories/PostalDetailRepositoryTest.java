package com.postal.service.repositories;

import com.postal.service.AbstractRepositoryTestBase;
import com.postal.service.config.TestConfigurations;
import com.postal.service.model.PostalDetail;
import io.r2dbc.postgresql.util.Assert;
import org.junit.Test;
import org.junit.runner.OrderWith;
import org.junit.runner.manipulation.Alphanumeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;

import java.util.Arrays;

@Import(TestConfigurations.DatabaseConfig.class)
@OrderWith(Alphanumeric.class)
public class PostalDetailRepositoryTest extends AbstractRepositoryTestBase {

    @Autowired
    private PostalDetailRepository postalDetailRepository;

    @Test
    public void test1Insert(){
        PostalDetail postalDetail1 = createPostalDetails("city1", "area1", "123456");
        PostalDetail postalDetail2 = createPostalDetails("city2", "area1", "123457");
        PostalDetail postalDetail3 = createPostalDetails("city1", "area2", "1234578");
        PostalDetail postalDetail4 = createPostalDetails("city2", "area2", "1234579");

        postalDetailRepository
                .saveAll(Arrays.asList(postalDetail1,postalDetail2,postalDetail3,postalDetail4))
        .blockLast();

        Assert.isTrue(postalDetail1.getId() == 1, "Id is 1");
        Assert.isTrue(postalDetail2.getId() == 2, "Id is 2");
        Assert.isTrue(postalDetail3.getId() == 3, "Id is 3");
        Assert.isTrue(postalDetail4.getId() == 4, "Id is 4");
    }

    @Test
    public void test2FindByCityNameIgnoreCase(){
        StepVerifier.create(postalDetailRepository.findByCityNameIgnoreCase("City1"))
                .expectNextMatches(pd -> pd.getPostalCode().equals("123456"))
                .expectNextMatches(pd -> pd.getPostalCode().equals("1234578"))
                .verifyComplete();
    }

    @Test
    public void test3FindByAreaNameContainingIgnoreCase(){
        StepVerifier.create(postalDetailRepository.findByAreaNameContainingIgnoreCase("Area1"))
                .expectNextMatches(pd -> pd.getCityName().equals("city1"))
                .expectNextMatches(pd -> pd.getCityName().equals("city2"))
                .verifyComplete();
    }

    private PostalDetail createPostalDetails(String city, String area, String postalCode){
        PostalDetail postalDetail = new PostalDetail();
        postalDetail.setCityName(city);
        postalDetail.setAreaName(area);
        postalDetail.setPostalCode(postalCode);

        return postalDetail;
    }
}
