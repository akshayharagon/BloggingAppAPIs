package bloggApp_apis.PayloadsOrDTOs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

	private int id;
	@NotBlank
	@Size(min=5)
	private String category;
	@NotBlank
	@Size(min=10)
	private String categoyDescription;

}
